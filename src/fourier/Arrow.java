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

    private double realPart;
    private double imgPart;
    private double rotSpeed;

    public Arrow(double mag, double a, double speed) {
        realPart = mag * Math.cos(a);
        imgPart = mag * Math.sin(a);

        rotSpeed = speed;
    }

    public Arrow() {
        this(0, 0, 0);
    }

    public void evolve(double period) {
        double dAngle = period * rotSpeed;

        double cosAngle = Math.cos(dAngle);
        double sinAngle = Math.sin(dAngle);

        double newRealPart = realPart * cosAngle - imgPart * sinAngle;
        double newImgPart = realPart * sinAngle + imgPart * cosAngle;
        realPart = newRealPart;
        imgPart = newImgPart;
    }

    public void paint(Graphics g, double panelHeight, double x0, double y0, double zoom) {

        // Coordinates of the end of the arrow
        double x = getX();
        double y = getY();

        int xApp = (int) (x * zoom + x0);
        int yApp = g.getClipBounds().height - (int) (y * zoom + y0);

        g.setColor(Color.black);
        g.drawLine((int) x0, (int) (panelHeight - y0), xApp, yApp);
        g.setColor(Color.blue);
        int testRad = 5;
        g.drawOval(xApp - testRad, yApp - testRad, 2 * testRad + 1, 2 * testRad + 1);
    }

    public double getX() {
        return realPart;
    }

    public double getY() {
        return imgPart;
    }

    public Point2d getTip() {
        return new Point2d(realPart, imgPart);
    }

    public void add(Arrow toAdd) {
        this.realPart += toAdd.realPart;
        this.imgPart += toAdd.imgPart;
    }
}
