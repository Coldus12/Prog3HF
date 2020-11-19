package swingingAround;

import javax.swing.*;
import java.awt.*;

public class PaintPanel extends JPanel {

    private Screen screen;
    //private int pixelX, pixelY;
    private int width,  height;
    //Console console;

    public PaintPanel(int widht, int height,int screenX, int screenY) {
        this.width = widht;
        this.height = height;
        screen = new Screen(screenX, screenY);
        //pixelX = this.getWidth()/screen.getWidth();
        //pixelY = this.getHeight()/screen.getHeight();
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

    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color c) {
        screen.drawTriangle(x1,y1,x2,y2,x3,y3,c);
    }

    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        screen.drawLine(x1,y1,x2,y2,c);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color c) {
        screen.fillTriangle(x1, y1, x2, y2, x3, y3, c);
    }
}
