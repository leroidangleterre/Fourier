/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourier;

/**
 *
 * @author arthurmanoha
 */
public class Fourier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        World world = new WorldReal();
        GraphicPanel panel = new GraphicPanel(world);
        Window window = new Window(panel);

        // Compute the fourier parameters from a given set of points
        // Display the points and the curve obtained via the Fourier transform.
    }

}
