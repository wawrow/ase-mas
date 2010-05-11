/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

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

    @Override
    public void update(Observable o, Object arg){
        Grid grid = (Grid)o;
        for(AbstractNanobot anb: grid.getNanobotPositionMap().keySet()){
            GridCell cell = grid.getNanobotPositionMap().get(anb);
           System.out.println(anb.hashCode() + " : "  +cell.getX() + "," + cell.getY());
        }

    }

}
