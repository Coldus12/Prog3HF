package swingingAround.Entities;

import swingingAround.ThreeD.Vec3;

import java.awt.*;

/**
 * Ponthoz tartozó entitás.
 */
public class PointEntity extends Entity {
    /**
     * Hány darab pont lett létrehozva amióta a program elindult
     * <p>
     *     Az egyetlen szerepe ennek a változónak az,
     *     hogy biztosítsa azt, hogy minden pont
     *     rendelkezik egy különböző, egyedi id-val.
     * </p>
     */
    private static int number;

    /**
     * Maga a pont Vec3-ként.
     */
    private Vec3 point;

    /**
     * A konstruktor - létrehoz egy pontot, és beállítja a színét
     * <p>
     *     Beállítja a pont koordinátáit, színét, egyedi azonosítóját,
     *     nevét, és a képletét.
     * </p>
     * @param point maga a pont Vec3 formájában
     * @param c a pont színe
     */
    public PointEntity(Vec3 point, Color c) {
        this.point = point;
        setColor(c);
        number++;
        setId("Point"+number);
        setName("Point"+number);

        setFormula("Point " + point.x + " " + point.y + " " + point.z);
    }

    public void setPoint(Vec3 newPoint) {
        point = newPoint;
    }

    public Vec3 getPoint() {
        return point;
    }

    @Override
    public Mathfunction[] getMathfunctions() {
        return new Mathfunction[0];
    }
}
