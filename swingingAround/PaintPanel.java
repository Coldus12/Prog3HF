package swingingAround;

import swingingAround.ThreeD.Screen;

import javax.swing.*;
import java.awt.*;

/**
 * Egy módosított JPanel, aminek a legfontosabb tulajdonsága, hogy
 * rendelkezik egy Screennel, aminek a képét kirajzolja.
 */
public class PaintPanel extends JPanel {

    /**
     * A panelhez tartozó, kirajzolt Screen.
     */
    private Screen screen;
    /**
     * A Panel szélessége, és magassága.
     */
    private int width,  height;

    /**
     * Az osztály konstruktora - beállítja a panel méreteit, és a Screen felbontását.
     * @param widht panel szélessége
     * @param height panel magassága
     * @param screenX Screen felbontásának szélessége
     * @param screenY Screen felbontásának magassága
     */
    public PaintPanel(int widht, int height,int screenX, int screenY) {
        this.width = widht;
        this.height = height;
        screen = new Screen(screenX, screenY);
    }

    /**
     * Beállít egy új Screen-t amit ki kell rajzolnia.
     * @param newScreen az új Screen
     */
    public void setScreen(Screen newScreen) {
        screen = newScreen;
    }

    public Screen getScreen() {return screen;}

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width,height);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawImage(screen.getImage(),0,0,width,height,null);
    }
}
