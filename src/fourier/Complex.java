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
class Complex {

    private double realPart;
    private double imgPart;
    private double rotSpeed;

    public Complex(double real, double img, double speed) {

        realPart = real;
        imgPart = img;
//        realPart = mag * Math.cos(a);
//        imgPart = mag * Math.sin(a);

        rotSpeed = speed;
    }

    public Complex() {
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
        paint(g, panelHeight, x0, y0, zoom, Color.blue);
    }

    public void paint(Graphics g, double panelHeight, double x0, double y0, double zoom, Color c) {

        // Coordinates of the end of the arrow
        double x = getX();
        double y = getY();

        int xApp = (int) (x * zoom + x0);
        int yApp = g.getClipBounds().height - (int) (y * zoom + y0);

        g.setColor(Color.black);
        g.drawLine((int) xApp, (int) (panelHeight - y0), xApp, yApp);
        g.setColor(c);
//        System.out.println("--> " + c);
        int testRad = 5;
        g.fillOval(xApp - testRad, yApp - testRad, 2 * testRad + 1, 2 * testRad + 1);
    }

    public double getX() {
        return realPart;
    }

    public double getY() {
        return imgPart;
    }

    public double getMagnitude() {
        return Math.sqrt(realPart * realPart + imgPart * imgPart);
    }

    public double getPhase() {
        return Math.atan2(imgPart, realPart);
    }

    public void setAngle(double newAngle) {
        double mag = getMagnitude();
        realPart = mag * Math.cos(newAngle);
        imgPart = mag * Math.sin(newAngle);
    }

    public Point2d getTip() {
        return new Point2d(realPart, imgPart);
    }

    public void add(Complex toAdd) {
        if (toAdd != null) {
            this.realPart += toAdd.realPart;
            this.imgPart += toAdd.imgPart;
        }
    }

    public void add(double rePart, double imPart) {
        this.realPart += rePart;
        this.imgPart += imPart;
    }

    public String toString() {
        return "(" + realPart + " + " + imgPart + "j)";
    }
}
