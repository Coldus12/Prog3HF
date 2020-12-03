package swingingAround.Entities;

import java.awt.*;

/**
 * A térbeli függvény reprezentációja.
 */
public abstract class Mathfunction {
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
}
