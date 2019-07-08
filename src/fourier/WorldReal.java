/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourier;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.PI;
import java.util.ArrayList;

/**
 *
 * @author arthurmanoha
 */
public class WorldReal extends World {

    // We describe all the components of the Fourier transform,
    // i.e. the rotating arrows, i.e. their length and rotational speed.
    private double width, height;
    private boolean isRunning;
    private double date;

    private ArrayList<Complex> arrows;
    private Complex currentSum;

    private ArrayList<Point2d> track;

    int N;
    double deltaX;
    Complex fourierCoefs[];
    double amplitudes[];
    double phases[];

//    double values[] = {1, 4, 3, 2};
    double values[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Real Square function
//        double values[] = {0, 0, 0, 1, 1, 1, 0, 0, 0, 2, 2, 2, 0, 0, 0}; // other Square function

    private int nbHarmonicsVisible = 6;

    public WorldReal() {
        width = 100;
        height = 720;
        date = 0;

        currentSum = new Complex();

        N = values.length;

        deltaX = 1.0 / N;

        fourierCoefs = new Complex[N];
        amplitudes = new double[N];
        phases = new double[N];

        // Computing Cn :
        for (int n = 0; n < N; n++) {
            fourierCoefs[n] = new Complex();
            for (int k = 0; k < N; k++) {
//                double frequency = 2*PI*n/(N*deltaX);
                fourierCoefs[n].add(values[k] * Math.cos(-2 * PI * n * k / N),
                        values[k] * Math.sin(-2 * PI * n * k / N));
                amplitudes[n] = fourierCoefs[n].getMagnitude();
                phases[n] = fourierCoefs[n].getPhase();
            }
            System.out.println("coef " + n + " : " + fourierCoefs[n]);
        }

        track = new ArrayList<>();

        track.add(currentSum.getTip());
    }

    public void evolve(double period) {
        for (Complex a : fourierCoefs) {
            if (a != null) {
                a.evolve(period);
            }
        }
        date++;

        currentSum = new Complex();
        for (Complex a : fourierCoefs) {
            currentSum.add(a);
        }
        track.add(currentSum.getTip());
    }

    public void paint(Graphics g, double panelHeight, double x0, double y0, double zoom) {
//        System.out.println("WorldReal.paint: begin");

        Color c = Color.blue;

        for (double x = 0; x < 1; x += deltaX / 10) {
            double value = 0;
            for (int n = 0; n < Math.min(nbHarmonicsVisible, fourierCoefs.length); n++) {
                double magnitude = fourierCoefs[n].getMagnitude();
                double frequency = 2 * PI * n / (N * deltaX);
                double phase = fourierCoefs[n].getPhase();
                value += magnitude * Math.cos(frequency * x + phase);
            }
            // TODO: why divide y ?
            Complex point = new Complex(x, value / 40, 0);
            point.paint(g, panelHeight, x0, y0, zoom, c);
        }

        // Display the data points
        c = Color.red;
        for (int i = 0; i < values.length; i++) {
            double x = i * deltaX;
            double y = values[i];
            Complex dataPoint = new Complex(x, y, 0);
            dataPoint.paint(g, panelHeight, x0, y0, zoom, c);
        }

//        for (int n = 0; n < fourierCoefs.length; n++) {
//            Complex a = fourierCoefs[n];
//            if (a != null) {
//                System.out.println("WorldReal, painting with coef " + a);
//                a.paint(g, panelHeight, x0 + dX0 * zoom, y0 + dY0 * zoom, zoom);
//                dX0 += a.getX();
//                dY0 += a.getY();
//            }
//        }
//
//        for (Point2d trackPoint : track) {
//            trackPoint.paint(g, panelHeight, x0, y0, zoom);
//        }
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
        for (Complex a : fourierCoefs) {
            a.setAngle(0.1 * (Math.random() - 0.5));
        }
    }

    public void increaseNbHarmonics(int increment) {
        nbHarmonicsVisible = Math.min(Math.max(0, nbHarmonicsVisible + increment), values.length);
        System.out.println("nb harmonics: " + nbHarmonicsVisible);
    }
}
