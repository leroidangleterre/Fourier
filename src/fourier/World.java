/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourier;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author arthurmanoha
 */
public class World {

    // We describe all the components of the Fourier transform,
    // i.e. the rotating arrows, i.e. their length and rotational speed.
    private double width, height;
    private boolean isRunning;
    private double date;

    private ArrayList<Arrow> arrows;

    public World() {
        width = 100;
        height = 720;
        date = 0;

        arrows = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double length = 12 - i;
            double rotSpeed = (double) i / 10 + 0.1;
            Arrow newArrow = new Arrow(length, 0, rotSpeed);
            arrows.add(newArrow);
        }
    }

    public void evolve(double period) {
        for (Arrow a : arrows) {
            a.evolve(period);
        }
        date++;
    }

    public void paint(Graphics g, double panelHeight, double x0, double y0, double zoom) {

        double dX0 = 0;
        double dY0 = 0;
        for (Arrow a : arrows) {
            a.paint(g, panelHeight, x0 + dX0 * zoom, y0 + dY0 * zoom, zoom);
            dX0 += a.getX();
            dY0 += a.getY();
        }
    }

    public double getWidth() {
        return this.width;
    }

    public void play() {
        if (!this.isRunning) {
            this.isRunning = true;
        }

    }

    public void pause() {
        if (this.isRunning) {
            this.isRunning = false;
        }

    }

    public double getDate() {
        return this.date;
    }

}
