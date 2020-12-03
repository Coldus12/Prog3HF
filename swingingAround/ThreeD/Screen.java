package swingingAround.ThreeD;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Adott felbontású képre való rajzoláshoz osztály.
 */
public class Screen {

    /**
     * A kép oszlopainak száma - azaz a kép pixelben mért szélessége
     */
    private final int nrOfColumns;
    /**
     * A kép osorainak száma - azaz a kép pixelben mért magassága
     */
    private final int nrOfRows;

    /**
     * A kép amelyre a rajzol az osztály.
     */
    private BufferedImage matrix;

    public Screen(int columns, int rows) {
        nrOfColumns = columns;
        nrOfRows = rows;
        matrix = new BufferedImage(nrOfColumns,nrOfRows, BufferedImage.TYPE_INT_RGB);
    }

    public Color getColorAt(int x, int y) {
        if (checkIfCoordsAreInsideBounds(x,y))
            return new Color(matrix.getRGB(x,y));
        return Color.BLACK;
    }

    public void setColorAt(int x, int y, Color c) {
        if (checkIfCoordsAreInsideBounds(x,y))
            matrix.setRGB(x,y,c.getRGB());
    }

    public int getWidth() {
        return nrOfColumns;
    }

    public int getHeight() {
        return nrOfRows;
    }

    public BufferedImage getImage() {
        return matrix;
    }

    /**
     * Rajzol egy vonalat a képre két pont közé
     * @param x1 első pont x koordinátája
     * @param y1 első pont y koordinátája
     * @param x2 második pont x koordinátája
     * @param y2 második pont y koordinátája
     * @param c a vonal színe
     */
    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        Graphics2D g = (Graphics2D) matrix.getGraphics();
        g.setColor(c);
        g.drawLine(x1,y1,x2,y2);
        g.dispose();
    }

    /**
     * Megvizsgálja, hogy egy adott pont a képen belül, vagy kívül található
     * @param x1 a pont x koordinátája
     * @param y1 a pont y koordinátája
     * @return a képben van-e ez a pont
     */
    private boolean checkIfCoordsAreInsideBounds(int x1, int y1) {
        boolean ret = false;

        if (((x1 >= 0) && (x1 < getWidth())) && (y1 >= 0) && (y1 < getHeight()))
            ret = true;

        return ret;
    }

    /**
     * Rajzol egy "pontot" adott színnel a képre.
     * @param x a pont x koordinátája
     * @param y a pont y koordinátája
     * @param size a pont mérete
     * @param c a pont színe
     */
    public void drawPoint(int x, int y, int size, Color c) {
        if (checkIfCoordsAreInsideBounds(x,y)) {
            Graphics2D g = (Graphics2D) matrix.getGraphics();
            g.setColor(c);
            g.drawOval(x,y,size,size);
            g.dispose();
        }
    }

    /**
     * Kirajzol egy háromszöget a képre.
     * @param x1 az első pont x koordinátája
     * @param y1 az első pont y koordinátája
     * @param x2 a második pont x koordinátája
     * @param y2 a második pont y koordinátája
     * @param x3 a harmadik pont x koordinátája
     * @param y3 a harmadik pont y koordinátája
     * @param c a háromszög színe
     */
    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color c) {
        int[] xs = {x1, x2, x3};
        int[] ys = {y1, y2, y3};
        Graphics2D g = (Graphics2D) matrix.getGraphics();
        g.setColor(c);
        g.drawPolygon(xs,ys,3);
        g.dispose();
    }

    /**
     * Kirajzol egy háromszöget a képre.
     * @param tri a kirajzolandó háromszög.
     */
    public void drawTriangle(Triangle tri) {
        if (tri != null) {
            Vec3 v1 = tri.v1;
            Vec3 v2 = tri.v2;
            Vec3 v3 = tri.v3;

            drawTriangle((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y, tri.c);
        }
    }

    /**
     * Kirajzol egy teli háromszöget a képre.
     * @param x1 az első pont x koordinátája
     * @param y1 az első pont y koordinátája
     * @param x2 a második pont x koordinátája
     * @param y2 a második pont y koordinátája
     * @param x3 a harmadik pont x koordinátája
     * @param y3 a harmadik pont y koordinátája
     * @param c a háromszög színe
     */
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color c) {

        int[] xs = {x1, x2, x3};
        int[] ys = {y1, y2, y3};

        Graphics2D g = (Graphics2D) matrix.getGraphics();
        g.setColor(c);
        g.fillPolygon(xs,ys,3);
        g.dispose();
    }

    /**
     * Kirajzol egy teli háromszöget a képre.
     * @param tri a kirajzolandó háromszög.
     */
    public void fillTriangle(Triangle tri) {
        if (tri != null) {
            Vec3 v1 = tri.v1;
            Vec3 v2 = tri.v2;
            Vec3 v3 = tri.v3;

            fillTriangle((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y, tri.c);
        }
    }

    /**
     * Kirajzol egy négyszöget a képre
     * @param x1 bal felső sarok x koordinátája
     * @param y1 bal felső sarok y koordinátája
     * @param width a négyszög szélessége
     * @param height a négyszög magassága
     * @param c a négyszög színe
     */
    public void drawRectangle(int x1, int y1, int width, int height, Color c) {
        Graphics2D g = (Graphics2D) matrix.getGraphics();
        g.setColor(c);
        g.drawRect(x1,y1,width,height);
        g.dispose();
    }

    /**
     * Kirajzol egy teli négyszöget a képre
     * @param x1 bal felső sarok x koordinátája
     * @param y1 bal felső sarok y koordinátája
     * @param width a négyszög szélessége
     * @param height a négyszög magassága
     * @param c a négyszög színe
     */
    public void fillRectangle(int x1, int y1, int width, int height, Color c) {
        Graphics2D g = (Graphics2D) matrix.getGraphics();
        g.setColor(c);
        g.fillRect(x1,y1,width,height);
        g.dispose();
    }

}
