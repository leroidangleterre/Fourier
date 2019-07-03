/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourier;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author arthurmanoha
 */
class Point2d {

    private double x, y;

    public Point2d(double xParam, double yParam) {
        x = xParam;
        y = yParam;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void paint(Graphics g, double panelHeight, double x0, double y0, double zoom) {

        int radiusApp = 1;

        int xApp = (int) (x * zoom + x0);
        int yApp = g.getClipBounds().height - (int) (y * zoom + y0);
        g.setColor(Color.red);

        g.fillOval(xApp - radiusApp, yApp - radiusApp, 2 * radiusApp + 1, 2 * radiusApp + 1);
    }
}
