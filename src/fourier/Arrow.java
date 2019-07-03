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
 * @author arthurmanoha Representation of a complex number displayed with an
 * arrow.
 */
class Arrow {

    private double magnitude;
    private double angle;
    private double rotSpeed;

    public Arrow(double mag, double a, double speed) {
        magnitude = mag;
        angle = a;
        rotSpeed = speed;
    }

    public Arrow() {
        this(1, 0, 0);
    }

    public void evolve(double period) {
        angle += period * rotSpeed;
    }

    public void paint(Graphics g, double panelHeight, double x0, double y0, double zoom) {

        // Coordinates of the end of the arrow
        double x = getX();
        double y = getY();

        int xApp = (int) (x * zoom + x0);
        int yApp = g.getClipBounds().height - (int) (y * zoom + y0);

        g.setColor(Color.black);
        g.drawLine((int) x0, (int) (panelHeight - y0), xApp, yApp);
    }

    public double getX() {
        return magnitude * Math.cos(angle);
    }

    public double getY() {
        return magnitude * Math.sin(angle);
    }
}
