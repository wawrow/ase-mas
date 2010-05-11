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
import javax.swing.JFrame;
import world.AbstractNanobot;
import world.Grid;
import world.GridCell;


/**
 *
 * @author Wawrzyniec
 */
public class WorldObserverSwing implements Observer{

    OutputStream stdin = null;
    InputStream stderr = null;
    InputStream stdout = null;

    @Override
    public void update(Observable o, Object arg){
        Grid grid = (Grid)arg;
        panel.worldMap = grid.getNanobotPositionMap();
//        for(AbstractNanobot anb: grid.getNanobotPositionMap().keySet()){
            //myFrame.trytopaintsomethingmore();
            panel.repaint();
//        }
    }

    JFrame myFrame;
    MyPanel panel;
    public void Start() throws IOException{
        panel = new MyPanel();
        myFrame = new JFrame("My Test");
        myFrame.setContentPane(panel);
        myFrame.pack();
        myFrame.setVisible(true);
/*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                myFrame.setVisible(true);
            }
        });
  */  }
    public void Stop(){
    }
}
