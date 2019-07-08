/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourier;

import java.awt.Graphics;
import static java.lang.Math.PI;
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

    private ArrayList<Complex> arrows;
    private Complex currentSum;

    private ArrayList<Point2d> track;

    public World() {
        width = 100;
        height = 720;
        date = 0;

        currentSum = new Complex();

        double realPartTab[] = {0, 1, 1, 0};
        double imgPartTab[] = {0, 0, 1, 1};
//        double realPartTab[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0};
//        double imgPartTab[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 7, 6, 5, 4, 3, 2, 1};
//        double realPartTab[] = {6, 5.5, 4.5, 2, 0, -2, -4.5, -5.5, -6, -5.5, -4.5, -2, 0, 2, 4.5, 5.5};
//        double imgPartTab[] = {0, 2, 4.5, 5.5, 6, 5.5, 4.5, 2, 0, -2, -4.5, -5.5, -6, -5.5, -4.5, -2};

        arrows = new ArrayList<>();
        // Computing Cn :
        for (int n = 0; n <= 5; n++) {

            // Loop twice: once for negative rotSpeed, once for positive rotSpeed.
            for (int rotSpeedSign = -1; rotSpeedSign <= 1; rotSpeedSign += 2) {
//                System.out.println("Loop on 'n': value " + n);
                //Determine the initial amplitude and phase of the arrows (current version: phase = 0;
//            double length;
//            if (i == 0) {
//                length = 1;
//            } else if (i % 2 == 0) {
//                length = 1.0 / i;
//            } else {
//                length = 1.0 / (i + 1);
//            }
                double realPartCn = 0;
                double imgPartCn = 0;

                for (int k = 0; k < realPartTab.length; k++) {
//                    System.out.println("Loop on 'k': value " + k);
                    double angleRad = -2 * PI * (double) n * (double) k;
                    double angleDeg = angleRad * 180 / PI;
                    double cos2 = Math.cos(angleDeg);
                    double sin2 = Math.sin(angleDeg);
//                    System.out.println("angle: " + angleDeg + "cos2: " + cos2 + ", sin2: " + sin2);
                    double realPartCnIncrement = cos2 * realPartTab[k] - sin2 * imgPartTab[k];
                    double imgPartCnIncrement = sin2 * realPartTab[k] + cos2 * imgPartTab[k];

//                    System.out.println("increments[" + k + "]: real: " + realPartCnIncrement + ", img: " + imgPartCnIncrement);
                    realPartCn += realPartCnIncrement;
                    imgPartCn += imgPartCnIncrement;
                }
//            realPartCn = realPartCn / realPartTab.length;
//            imgPartCn = imgPartCn / realPartTab.length;

//                System.out.println("real C[" + n + "]: " + realPartCn + ", img Cn: " + imgPartCn);
//                System.out.println("-----------------------------------------------------------");
                // Determine the rotation speed of each arrow;
                double rotSpeed = n * rotSpeedSign;
                Complex newArrow = new Complex(realPartCn, imgPartCn, rotSpeed);

                arrows.add(newArrow);
                currentSum.add(newArrow);
            }
        }

        track = new ArrayList<>();

        track.add(currentSum.getTip());
    }

    public void evolve(double period) {
        for (Complex a : arrows) {
            a.evolve(period);
        }
        date++;

        currentSum = new Complex();
        for (Complex a : arrows) {
            currentSum.add(a);
        }
        track.add(currentSum.getTip());
    }

    public void paint(Graphics g, double panelHeight, double x0, double y0, double zoom) {
        System.out.println("World.paint(...): begin");
        double dX0 = 0;
        double dY0 = 0;
        for (Complex a : arrows) {
            a.paint(g, panelHeight, x0 + dX0 * zoom, y0 + dY0 * zoom, zoom);
            dX0 += a.getX();
            dY0 += a.getY();
        }

        for (Point2d trackPoint : track) {
            trackPoint.paint(g, panelHeight, x0, y0, zoom);
        }
        System.out.println("World.paint(...): end");
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

    public void changeArrowAngles() {
        for (Complex a : arrows) {
            a.setAngle(0.1 * (Math.random() - 0.5));
        }
    }

    public void increaseNbHarmonics(int increment) {

    }
}
