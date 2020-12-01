package swingingAround;

import javax.swing.*;
import java.awt.*;

public class PaintPanel extends JPanel {

    private Screen screen;
    private int width,  height;

    public PaintPanel(int widht, int height,int screenX, int screenY) {
        this.width = widht;
        this.height = height;
        screen = new Screen(screenX, screenY);
    }

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
