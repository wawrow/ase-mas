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
public class WorldPanel extends JPanel {

    int size = 40;
    int i;
    Object lock = new Object();

    public WorldPanel() {
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
                switch (anb.getType()) {
                    case 0:
                        g2d.setPaint(Color.DARK_GRAY);
                        break;
                    case 1:
                        g2d.setPaint(Color.RED);
                        break;
                    case 2:
                        g2d.setPaint(Color.BLUE);
                        break;
                    case 3:
                        g2d.setPaint(Color.green);
                        break;
                    default:
                        g2d.setPaint(Color.BLACK);
                        break;
                }
                GridCell cell = worldMap.get(anb);
                //if (10 + size * cell.getX() + size - 2 > WorldPanel.WIDTH || 20 + size * cell.getY() + size - 2 > WorldPanel.HEIGHT) {
                //    size = (int) ((WorldPanel.WIDTH - 20) / (cell.getX()+1));
                //}
                if (size > 4) {
                    if (10 + (cell.getX() + 1) * size + size > this.getWidth()) {
                        size = (int) (this.getWidth() - 20) / (cell.getX() + 1);
                        this.repaint();
                        return;
                    }
                    if (10 + (cell.getY() + 1) * size + size> this.getHeight()) {
                        size = (int) (this.getHeight() - 20) / (cell.getY() + 1);
                        this.repaint();
                        return;
                    }
                }
                Rectangle rect = new Rectangle(10 + size * cell.getX(), 20 + size * cell.getY(), size - 2, size - 2);
                g2d.fill(rect);
            }
        }
        i++;
    }
}
