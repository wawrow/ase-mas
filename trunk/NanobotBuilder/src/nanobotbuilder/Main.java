package nanobotbuilder;

import com.agentfactory.afapl2.interpreter.AFAPL2ArchitectureFactory;
import com.agentfactory.platform.core.DuplicateAgentNameException;
import com.agentfactory.platform.core.IAgent;
import com.agentfactory.platform.core.NoSuchArchitectureException;
import com.agentfactory.platform.core.NoSuchServiceException;
import com.agentfactory.platform.impl.DefaultAgentPlatform;
import com.agentfactory.platform.impl.RoundRobinTimeSliceFixedScheduler;
import com.agentfactory.platform.impl.PlatformServiceManager;
import com.agentfactory.service.ams.AgentManagementService;
import com.agentfactory.service.mts.http.HTTPMessageTransportService;
import com.agentfactory.visualiser.Debugger;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import service.WorldService;

public class Main {
    public static void main(String[] args) {
        // Create a new agent platform with a basic name and domain
        DefaultAgentPlatform platform = new DefaultAgentPlatform();
        platform.setName("test");
        platform.setDomain("ucd.ie");

        // Install a scheduling algorithm for executing the agents
        platform.setScheduler(new RoundRobinTimeSliceFixedScheduler());

        // Install and register the AFAPL2 Architecture Factory:
        // This enables support for instantiating AFAPL2 agents (i.e. agents
        // whose source code is identified by a .agent extension)
        AFAPL2ArchitectureFactory factory = new AFAPL2ArchitectureFactory();
        Properties props = new Properties();
        props.setProperty("TIMESLICE", "25");
        factory.configure(props);
        platform.getArchitectureService().registerArchitectureFactory(factory);

        PlatformServiceManager manager = ((PlatformServiceManager) platform.getPlatformServiceManager());
        try {
            manager.addService(WorldService.class, "world");
        } catch (NoSuchServiceException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            props = new Properties();
            props.setProperty("port", "4444");
            manager.addService(HTTPMessageTransportService.class, HTTPMessageTransportService.NAME, 0, props);
        } catch (NoSuchServiceException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Install and start the Agent Factory Debugger
        Debugger debugger = new Debugger();
        debugger.init(new HashMap<String, String>(), platform);
        debugger.start();

        // Get a reference to the Agent Management Service so that the default
        // agent community can be created...
        AgentManagementService ams = (AgentManagementService) platform.getPlatformServiceManager().getServiceByName(AgentManagementService.NAME);
        try {
            // Create a bunch of nanobot agents, 3 of each type (3 types)
            int numberOfAgents = 9;
            for (int i=1; i<=numberOfAgents; i++) {
                // naming of agent is important "n#name" where the n tells the world service
                // what concrete nanobot class to create
                IAgent agent = ams.createAgent("1#nanobot" + i, "nanobotbuilder/NanobotType1.agent");
                agent.initialise("ALWAYS(BELIEF(supervisor(supervisor, addresses(http://localhost:4444/acc))))");
            }
/*
            for (int i=1; i<=numberOfAgents; i++) {
                // naming of agent is important "n:name" where the n tells the world service
                // what concrete nanobot class to create
                IAgent agent = ams.createAgent("2#nanobot" + i, "nanobotbuilder/NanobotType2.agent");
                agent.initialise("ALWAYS(BELIEF(supervisor(supervisor, addresses(http://localhost:4444/acc))))");
            }

            for (int i=1; i<=numberOfAgents; i++) {
                // naming of agent is important "n:name" where the n tells the world service
                // what concrete nanobot class to create
                IAgent agent = ams.createAgent("3#nanobot" + i, "nanobotbuilder/NanobotType3.agent");
                agent.initialise("ALWAYS(BELIEF(supervisor(supervisor, addresses(http://localhost:4444/acc))))");
            }
*/
            // Create the single supervisor
            ams.createAgent("supervisor", "nanobotbuilder/Supervisor.agent");
            
        } catch (NoSuchArchitectureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DuplicateAgentNameException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
