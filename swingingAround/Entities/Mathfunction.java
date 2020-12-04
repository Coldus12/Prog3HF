package swingingAround.Entities;

import swingingAround.ThreeD.Vec3;
import swingingAround.ThreeD.dMatrix;

import java.awt.*;

/**
 * A térbeli függvény reprezentációja.
 */
public abstract class Mathfunction {
    /**
     * Egy adott méretű mátrix, melyben a függvény egy
     * egy adott mennyiségű a függvénnyel előre kiszámított
     * értéket tárol, így azokat elegendő csak a megfelelő helyen
     * megjeleníteni.
     */
    private dMatrix mat;

    public Mathfunction(Axis axis) {
        currentAxis = axis;
    }

    /**
     * A függvény színe.
     */
    private Color color = Color.GREEN;

    /**
     * Az Axis azt szabja meg, hogy melyik tengelyhez tartozik a hozzárendelés.
     */
    public enum Axis {x, y, z, notDefined}

    /**
     * Egy konkrét függvény mire van kifejezve.
     * Alapból notDefined, és amíg az nem változik
     * nem is lesz kirajzolva.
     */
    private Axis currentAxis = Axis.notDefined;

    public Axis getCurrentAxis() {return currentAxis;}
    public void setCurrentAxis(Axis axis) {currentAxis = axis;}

    public Color getColor() {return color;}
    public void setColor(Color c) {color = c;}

    /**
     * Az osztály legfontosabb függvénye, maga a térbeli függvény.
     * <p>
     *     Kiszámítja a függvény értékét egy adott x,y,z pontban.
     *     A rajzolás folyamán az tengely amihez a függvény
     *     horrárendel mindig 0 értéket kap. Azaz ha van egy olyan
     *     kifejezés, mint: y=y+2, akkor mivel ez y-ra van kifejezve
     *     ez mindig olyan lesz, mint ha y=2 lenne.
     * </p>
     * @param x egy pont x koordinátája
     * @param y egy pont y koordinátája
     * @param z egy pont z koordinátája
     * @return a függvény értéke az adott pontban.
     */
    public abstract float exec3DFunction(float x, float y, float z);

    /**
     * Kiszámolja egy adott középponthoz tartozó "width" széles, "height" magas
     * mátrixba a függvény értékeit, így azokat később nem feltétlenül kell
     * újraszámolni.
     * @param middle a pont amit úgy vesz, mintha a mátrix közepe lenne
     * @param width a mátrix szélessége
     * @param height a mátrix magassága
     * @param stepSize a lépésméret a számításhoz
     */
    public void calcMat(Vec3 middle, int width, int height, double stepSize) {
        width += 1;
        height += 1;
        mat = new dMatrix(width,height);

        double xInLoop = 0;
        double yInLoop = 0;
        double zInLoop = 0;

        float currentX = 0;
        float currentY = 0;
        float currentZ = 0;

        switch (getCurrentAxis()) {
            case x:
                yInLoop = width/2.0 * stepSize;
                zInLoop = height/2.0 * stepSize;

                currentY = (float) (middle.y - yInLoop);
                currentZ = (float) (middle.z - zInLoop);

                for (int i = 0; i < width; i++) {
                    currentY += stepSize;
                    for (int j = 0; j < height; j++) {
                        currentZ += stepSize;

                        mat.setValueAt(i,j,exec3DFunction(0, currentY, currentZ));
                    }
                    currentZ = (float) (middle.z - zInLoop);
                }
                break;
            case y:
                xInLoop = width/2.0 * stepSize;
                zInLoop = height/2.0 * stepSize;

                currentX = (float) (middle.x - xInLoop);
                currentZ = (float) (middle.z - zInLoop);

                for (int i = 0; i < width; i++) {
                    currentX += stepSize;
                    for (int j = 0; j < height; j++) {
                        currentZ += stepSize;

                        mat.setValueAt(i,j,exec3DFunction(currentX, 0, currentZ));
                    }
                    currentZ = (float) (middle.z - zInLoop);
                }
                break;
            case z:
                xInLoop = width/2.0 * stepSize;
                yInLoop = height/2.0 * stepSize;

                currentX = (float) (middle.x - xInLoop);
                currentY = (float) (middle.y - yInLoop);

                for (int i = 0; i < width; i++) {
                    currentX += stepSize;
                    for (int j = 0; j < height; j++) {
                        currentY += stepSize;

                        mat.setValueAt(i,j,exec3DFunction(currentX, currentY, 0));
                    }
                    currentY = (float) (middle.y - yInLoop);
                }
                break;
        }
    }

    public double getMatValueAt(int x, int y) {
        return mat.getValueAt(x,y);
    }


}
