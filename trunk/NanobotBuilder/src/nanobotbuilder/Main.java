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
        props.setProperty("TIMESLICE", "100");
        factory.configure(props);
        platform.getArchitectureService().registerArchitectureFactory(factory);

        PlatformServiceManager manager = ((PlatformServiceManager) platform.getPlatformServiceManager());
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
            // 1. Create an agent
            IAgent agent = ams.createAgent("nanobot1", "nanobotbuilder/NanobotType1.agent");
            agent.initialise("BELIEF(needTask(supervisor, addresses(http://localhost:4444/acc)))");
            agent = ams.createAgent("supervisor", "nanobotbuilder/Supervisor.agent");
            // 2. Give it initial beliefs / goals
            // agent.initialise("BELIEF(happy)");
            // 3. Resume the agent (start it)
            // ams.resumeAgent("fergus");
        } catch (NoSuchArchitectureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DuplicateAgentNameException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
