package swingingAround.ThreeD;

import java.awt.*;

/**
 * Térbeli háromszög. Három Vec3-ból és egy színből áll.
 */
public class Triangle {

    /**
     * A háromszög három csúcsa.
     */
    public Vec3 v1, v2, v3;
    /**
     * Háromszög színe
     */
    public Color c;

    /**
     * Háromszög konstruktora ami beállítja a csúcsait, és színét
     * @param v1 egyik csúcs
     * @param v2 másik csúcs
     * @param v3 harmadik csúcs
     * @param c a háromszög színe
     */
    public Triangle(Vec3 v1, Vec3 v2, Vec3 v3, Color c) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.c = c;
    }

    /**
     * Fölülírt toString, ami kiírja a háromszög csúcsait, és színének az RGBA értékeit.
     * @return
     */
    public String toString() {
        return v1 + " " + v2 + " " + v3 + " Color: " + c.getAlpha() + " " + c.getRed() + " " + c.getGreen() + " " + c.getBlue();
    }

}
