/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import world.AbstractNanobot;
import world.GridCell;

/**
 *
 * @author Wawrzyniec
 */
public class MyPanel extends JPanel {

    int i;
    Object lock = new Object();

    public MyPanel() {
        i = 0;
        setPreferredSize(new Dimension(800, 600));
    }
    public Map<AbstractNanobot, GridCell> worldMap;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (worldMap != null) {
            for (AbstractNanobot anb : worldMap.keySet()) {
                switch(anb.getType()){
                    case 1: g2d.setPaint(Color.RED); break;
                    case 2: g2d.setPaint(Color.BLUE); break;
                    case 3: g2d.setPaint(Color.green); break;
                    default: g2d.setPaint(Color.BLACK); break;
                }
                GridCell cell = worldMap.get(anb);
                Rectangle rect = new Rectangle(10 + 20 * cell.getX(), 20 + 20 * cell.getY(), 18, 18);
                g2d.fill(rect);
            }
        }
        System.out.println("Painting Component.");
        i++;
    }
}
