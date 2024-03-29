package fourier;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    private GraphicPanel panel;

    public KeyboardListener(GraphicPanel p) {
        super();
        this.panel = p;
//        System.out.println("New KeyboardListener");
    }

    public void keyPressed(KeyEvent e) {
//        System.out.println("KeyPressed: '" + e.getKeyChar() + "'");
        switch (e.getKeyChar()) {
            case '0':
                panel.resetView();
                break;
            case '4':
                panel.swipe(-1, 0);
                break;
            case '6':
                panel.swipe(+1, 0);
                break;
            case '8':
                panel.swipe(0, +1);
                break;
            case '2':
                panel.swipe(0, -1);
                break;
            case ' ':
                panel.evolve(0.1);
                break;
            case 'p':
                panel.togglePlayPause();
                break;
            case '+':
                panel.evolve();
                break;
            case '-':
                panel.evolve(false);
                break;
            case '.':
                panel.changeArrowAngles();
                break;
            default:
                break;
        }
        switch (e.getKeyCode()) {

            case KeyEvent.VK_LEFT:
                panel.increaseNbHarmonics(-1);
                panel.repaint();
                break;
            case KeyEvent.VK_RIGHT:
                panel.increaseNbHarmonics(+1);
                panel.repaint();
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        // System.out.println("KeyReleased: ");
    }

    public void keyTyped(KeyEvent e) {
        // System.out.println("KeyTyped: ");
    }

}
