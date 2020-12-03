package swingingAround.Entities;

import swingingAround.ThreeD.Vec3;

import java.awt.*;

/**
 * Gömbhöz tartozó entitás
 */
public class SphereEntity extends Entity {
    /**
     * Hány darab gömb lett létrehozva amióta a program elindult
     * <p>
     *     Az egyetlen szerepe ennek a változónak az,
     *     hogy biztosítsa azt, hogy minden gömb
     *     rendelkezik egy különböző, egyedi id-val.
     * </p>
     */
    private static int number;

    /**
     * A gömb színe.
     */
    private Color color;
    /**
     * A gömbhöz tartozó függvények
     * <p>
     *     Egy gömbhöz valójában elég lenne két függvény is,
     *     lásd: y=-(25-x^2-z^2)^(1/2) és y=(25-x^2-z^2)^(1/2).
     *     Ezzel szemben itt négy függvény van, pusztán azért hogy
     *     esetleg szebben jelenjen meg. Kettő a gömböt y-ra fejezi ki,
     *     míg másik kettő z-re.
     * </p>
     */
    private final Mathfunction mf1,mf2, mf3, mf4;
    /**
     * A gömb középpontja.
     */
    private Vec3 center;
    /**
     * A gömb sugara.
     */
    private float radius;

    /**
     * A gömb konstruktora.
     * <p>
     *     Létrehozza a középponthoz és sugárhoz tartozó
     *     négy függvényt, beállítja az entitás id-ját,
     *     nevét, színét, képletét.
     * </p>
     * @param center gömb középpontja
     * @param radius gömb sugara
     * @param c gömb színe
     */
    public SphereEntity(Vec3 center, float radius, Color c) {
        this.center = center;
        this.radius = radius;
        this.color = c;

        number++;
        setId("sphere" + number);
        setName("sphere" + number);

        mf1 = new Mathfunction() {
            @Override
            public float exec3DFunction(float x, float y, float z) {
                float Xsquared = (float) Math.pow(x - center.x,2);
                float Zsquared = (float) Math.pow(z - center.z,2);

                return (float) (Math.sqrt(Math.pow(radius,2) - Xsquared - Zsquared) + center.y);
            }
        };
        mf1.setCurrentAxis(Mathfunction.Axis.y);

        mf2 = new Mathfunction() {
            @Override
            public float exec3DFunction(float x, float y, float z) {
                float Xsquared = (float) Math.pow(x - center.x,2);
                float Zsquared = (float) Math.pow(z - center.z,2);

                return (float) (-Math.sqrt(Math.pow(radius,2) - Xsquared - Zsquared) + center.y);
            }
        };
        mf2.setCurrentAxis(Mathfunction.Axis.y);

        mf3 = new Mathfunction() {
            @Override
            public float exec3DFunction(float x, float y, float z) {
                float Xsquared = (float) Math.pow(x - center.x,2);
                float Ysquared = (float) Math.pow(y - center.y,2);

                return (float) (Math.sqrt(Math.pow(radius,2) - Xsquared - Ysquared) + center.z);
            }
        };
        mf3.setCurrentAxis(Mathfunction.Axis.z);

        mf4 = new Mathfunction() {
            @Override
            public float exec3DFunction(float x, float y, float z) {
                float Xsquared = (float) Math.pow(x - center.x,2);
                float Ysquared = (float) Math.pow(y - center.y,2);

                return (float) (-Math.sqrt(Math.pow(radius,2) - Xsquared - Ysquared) + center.z);
            }
        };
        mf4.setCurrentAxis(Mathfunction.Axis.z);

        mf1.setColor(color);
        mf2.setColor(color);
        mf3.setColor(color);
        mf4.setColor(color);

        setFormula("Sphere " + center.x + " " + center.y + " " + center.z + " " + radius);
    }

    @Override
    public Mathfunction[] getMathfunctions() {
        Mathfunction[] mfs = new Mathfunction[4];
        mfs[0] = mf1;
        mfs[1] = mf2;
        mfs[2] = mf3;
        mfs[3] = mf4;
        return mfs;
    }

    @Override
    public void setColor(Color newColor) {
        color = newColor;
        mf1.setColor(color);
        mf2.setColor(color);
        mf3.setColor(color);
        mf4.setColor(color);
    }
}
