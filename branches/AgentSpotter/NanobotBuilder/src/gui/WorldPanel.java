/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Map;
import javax.swing.*;
import world.AbstractNanobot;
import world.AbstractObject;
import world.GridCell;
import world.Weld;

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
    public Map<AbstractObject, GridCell> weldMap;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (worldMap == null) {
            return;
        }
        this.recalculateSize();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (worldMap != null) {
            for (AbstractNanobot anb : worldMap.keySet()) {
                if (anb.isNanobot()) {
                    this.paintNanoBot(g2d, anb);
                }
            }
            for (AbstractObject aob : weldMap.keySet()) {
                if (aob instanceof Weld) {
                    this.paintWeld(g2d, (Weld) aob);
                }
            }

        }
        i++;
    }

    private void paintNanoBot(Graphics2D g2d, AbstractNanobot anb) {
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

        Shape shape = null;
        if (anb.getType() > 0) {
            shape = new Rectangle(10 + size * cell.getX(), 20 + size * cell.getY(), size - 2, size - 2);
        } else {
            shape = new Ellipse2D.Double(
                    (double) 10 + size * cell.getX(), (double) 20 + size * cell.getY(), (double) size - 2, (double) size - 2);
        }
        g2d.fill(shape);

    }

    private void paintWeld(Graphics2D g2d, Weld weld) {
        g2d.setPaint(Color.YELLOW);

        int sizeunit = ((int) size / 2);
        int startpointx = 10 + size * weld.endPointX1 + sizeunit;
        int startpointy = 20 + size * weld.endPointY1 + sizeunit;
        int endpointx = 10 + size * weld.endPointX2 + sizeunit;
        int endpointy = 20 + size * weld.endPointY2 + sizeunit;
        g2d.setStroke(new BasicStroke((int) size / 4));
//        g2d.drawLine(startpointx, startpointy, endpointx, endpointy);
        g2d.drawLine(endpointx, endpointy, startpointx, startpointy);
        //Line2D line = new Line2D.Double(startpointx, startpointy, endpointx, endpointy);
        //g2d.fill(line);

    }

    private void recalculateSize() {
        int maxx = 0;
        int maxy = 0;
        for (AbstractNanobot anb : worldMap.keySet()) {
            maxx = (maxx < worldMap.get(anb).getX() ? worldMap.get(anb).getX() : maxx);
            maxy = (maxy < worldMap.get(anb).getY() ? worldMap.get(anb).getY() : maxy);
        }

        if (size > 4) {
            if (10 + (maxx + 1) * size + size > this.getWidth()) {
                size = (int) (this.getWidth() - 20) / (maxx + 1);
            }
            if (10 + (maxy + 1) * size + size > this.getHeight()) {
                size = (int) (this.getHeight() - 20) / (maxy + 1);
            }
        }
    }
}
