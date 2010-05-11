/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Observer;
import world.AbstractNanobot;
import world.Grid;
import world.GridCell;

/**
 *
 * @author Wawrzyniec
 */
public class WorldObserver implements Observer {

    OutputStream stdin = null;
    InputStream stderr = null;
    InputStream stdout = null;

    @Override
    public void update(Observable o, Object arg){
        Grid grid = (Grid)arg;
        for(AbstractNanobot anb: grid.getNanobotPositionMap().keySet()){
            GridCell cell = grid.getNanobotPositionMap().get(anb);
            System.out.println(anb.hashCode() + " : "  +cell.getX() + "," + cell.getY());
            String message = "<message>"  + anb.hashCode() + " : "  +cell.getX() + "," + cell.getY() + "</message>\r\n";

            try{
                stdin.write(message.getBytes());
                stdin.flush();
            } catch (Exception ex){
               System.out.println(ex.toString());
            }
        }
    }

    public void Start() throws IOException{
      Process process = Runtime.getRuntime().exec ("c:\\guiTests.exe");
      stdin = process.getOutputStream ();
      stderr = process.getErrorStream ();
      stdout = process.getInputStream ();
      NewJFrame frame = new NewJFrame();
      frame.main(null);
    }
    public void Stop(){
        try{
        stdin.close();
        stderr.close();
        stdout.close();
        } catch (Exception ex){
            //Donothing
        }
    }
}
