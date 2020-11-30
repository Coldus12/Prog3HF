package swingingAround;

import java.awt.*;

public class SphereEntity extends Entity {
    private static int number;

    private Color color;
    private Mathfunction mf1,mf2, mf3, mf4;
    private Vec3 center;
    private float radius;

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
                //setCurrentAxis(Axis.y);
                float Xsquared = (float) Math.pow(x - center.x,2);
                float Zsquared = (float) Math.pow(z - center.z,2);

                return (float) (Math.sqrt(Math.pow(radius,2) - Xsquared - Zsquared) + center.y);
            }
        };
        mf1.setCurrentAxis(Mathfunction.Axis.y);

        mf2 = new Mathfunction() {
            @Override
            public float exec3DFunction(float x, float y, float z) {
                //setCurrentAxis(Axis.y);
                float Xsquared = (float) Math.pow(x - center.x,2);
                float Zsquared = (float) Math.pow(z - center.z,2);

                return (float) (-Math.sqrt(Math.pow(radius,2) - Xsquared - Zsquared) + center.y);
            }
        };
        mf2.setCurrentAxis(Mathfunction.Axis.y);

        mf3 = new Mathfunction() {
            @Override
            public float exec3DFunction(float x, float y, float z) {
                //setCurrentAxis(Axis.y);
                float Xsquared = (float) Math.pow(x - center.x,2);
                float Ysquared = (float) Math.pow(y - center.y,2);

                return (float) (Math.sqrt(Math.pow(radius,2) - Xsquared - Ysquared) + center.z);
            }
        };
        mf3.setCurrentAxis(Mathfunction.Axis.z);

        mf4 = new Mathfunction() {
            @Override
            public float exec3DFunction(float x, float y, float z) {
                //setCurrentAxis(Axis.y);
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
