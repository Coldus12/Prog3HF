package swingingAround;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen {

    private final int nrOfColumns;
    private final int nrOfRows;

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

    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        Graphics2D g = (Graphics2D) matrix.getGraphics();
        g.setColor(c);
        g.drawLine(x1,y1,x2,y2);
        g.dispose();
    }

    private boolean checkIfCoordsAreInsideBounds(int x1, int y1) {
        boolean ret = false;

        if (((x1 >= 0) && (x1 < getWidth())) && (y1 >= 0) && (y1 < getHeight()))
            ret = true;

        return ret;
    }

    public void drawPoint(int x, int y, int size, Color c) {
        if (checkIfCoordsAreInsideBounds(x,y)) {
            Graphics2D g = (Graphics2D) matrix.getGraphics();
            g.setColor(c);
            g.drawOval(x,y,size,size);
            g.dispose();
        }
    }

    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color c) {
        int[] xs = {x1, x2, x3};
        int[] ys = {y1, y2, y3};
        Graphics2D g = (Graphics2D) matrix.getGraphics();
        g.setColor(c);
        g.drawPolygon(xs,ys,3);
        g.dispose();
    }

    public void drawTriangle(Triangle tri) {
        if (tri != null) {
            Vec3 v1 = tri.v1;
            Vec3 v2 = tri.v2;
            Vec3 v3 = tri.v3;

            drawTriangle((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y, tri.c);
        }
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color c) {

        int[] xs = {x1, x2, x3};
        int[] ys = {y1, y2, y3};

        Graphics2D g = (Graphics2D) matrix.getGraphics();
        g.setColor(c);
        g.fillPolygon(xs,ys,3);
        g.dispose();
    }

    public void fillTriangle(Triangle tri) {
        if (tri != null) {
            Vec3 v1 = tri.v1;
            Vec3 v2 = tri.v2;
            Vec3 v3 = tri.v3;

            fillTriangle((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y, tri.c);
        }
    }

    public void drawRectangle(int x1, int y1, int width, int height, Color c) {
        Graphics2D g = (Graphics2D) matrix.getGraphics();
        g.setColor(c);
        g.drawRect(x1,y1,width,height);
        g.dispose();
    }

    public void fillRectangle(int x1, int y1, int width, int height, Color c) {
        Graphics2D g = (Graphics2D) matrix.getGraphics();
        g.setColor(c);
        g.fillRect(x1,y1,width,height);
        g.dispose();
    }

}
