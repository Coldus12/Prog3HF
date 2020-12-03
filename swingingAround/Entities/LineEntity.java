package swingingAround.Entities;

import swingingAround.ThreeD.Vec3;

import java.awt.*;

/**
 * Egyeneshez tartozó entitás.
 */
public class LineEntity extends Entity {
    /**
     * Hány darab egyenes lett létrehozva amióta a program elindult
     * <p>
     *     Az egyetlen szerepe ennek a változónak az,
     *     hogy biztosítsa azt, hogy minden egyenes
     *     rendelkezik egy különböző, egyedi id-val.
     * </p>
     */
    private static int number;

    /**
     * Egy pont ami rajta van az egyenesen, és az egyenes irányvektora.
     */
    private Vec3 direction, p1;

    /**
     * Az osztály konstruktora, mely két pontra ráilleszt egy egyenest.
     * @param p1 egyik pont
     * @param p2 másik pont
     * @param c az egyenes színe
     */
    public LineEntity(Vec3 p1, Vec3 p2, Color c) {
        number++;
        setColor(c);

        direction = new Vec3(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
        setId("line"+number);
        setName("line" + number);
        this.p1 = p1;
        setFormula("line " + p1.x + " " + p1.y + " " + p1.z + " " + p2.x + " " + p2.y + " " + p2.z);
    }

    /**
     * Visszaadja egy adott lambdára azt a pontot az egyenesről,
     * ami a p1-től lambdaszor irányvektorra van.
     * @param lambda lambda, skalár
     * @return az a vektor ami teljesíti a (p1 + lambda * direction)-t.
     */
    public Vec3 calcPartOfLine(float lambda) {
        return new Vec3(p1.x + lambda * direction.x, p1.y + lambda * direction.y, p1.z + lambda * direction.z);
    }

    public Vec3 getDirection() {return direction;}

    @Override
    public Mathfunction[] getMathfunctions() {
        return new Mathfunction[0];
    }

}