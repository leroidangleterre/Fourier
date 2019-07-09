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
//    double values[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
//        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Real Square function
//    double values[] = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    // Triangular wave
    double values[];
    private int nbHarmonicsVisible = 0;

    public WorldReal() {

        N = 1000;
        values = new double[N];
        for (int i = 0; i < N; i++) {
//            Triangular wave
            values[i] = i / (double) N - 0.5;
            if (values[i] < 0) {
                values[i] += 0.2;
            }
            // Modif on triangular wave:
            double fact = 0.3;
            if (i < N / 2) {
                values[i] += fact * Math.sin(i / (double) N * 2 * PI);
            } else {
                values[i] -= fact * Math.sin(i / (double) N * 2 * PI);
            }
            // Sine wave
//            values[i] = Math.sin((i / (double) N) * 2 * PI);
//            // Sum of sine waves
//            for (int j = 0; j < 19; j++) {
//                values[i] += (Math.exp(-j / 5)) * Math.sin((i / (double) N) * 2 * PI * j);
//            }
//            // Square function
//            values[i] = i < N / 2 ? 0 : 1;
        }
        width = 100;
        height = 720;
        date = 0;

        currentSum = new Complex();

        deltaX = 1.0 / N;

        fourierCoefs = new Complex[N];
        amplitudes = new double[N];
        phases = new double[N];

        // Computing Cn :
        for (int n = 0; n < N / 2; n++) { // N/2 is the highest significant frequency, cannot compute more.
            fourierCoefs[n] = new Complex();
            for (int k = 0; k < N; k++) {
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
                if (n > 0) {
                    magnitude *= 2; // The n>=1 components have twice the contribution.
                }
                double frequency = 2 * PI * n / (N * deltaX);
                double phase = fourierCoefs[n].getPhase();
                value += magnitude * Math.cos(frequency * x + phase);
            }
            // TODO: why divide y ?
            Complex point = new Complex(x, value / N, 0);
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

        // Trim the amount of visible harmonics to the significatnt ones (N/2 max).
        nbHarmonicsVisible = Math.min(nbHarmonicsVisible, N / 2);
        System.out.println("nb harmonics: " + nbHarmonicsVisible);
    }
}
