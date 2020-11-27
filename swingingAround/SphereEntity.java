package swingingAround;

import java.awt.*;

public class SphereEntity extends Entity {
    private static int number;

    private String formula;
    private String name;
    private Color color;
    private Mathfunction mf1,mf2, mf3, mf4;
    private Vec3 center;
    private float radius;
    private boolean exists;

    public SphereEntity(Vec3 center, float radius, Color c) {
        this.center = center;
        this.radius = radius;
        this.color = c;
        exists = true;

        number++;
        name = "sphere" + number;

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

        //System.out.println(mf1.getColor().toString());

        formula = "Sphere " + center.x + " " + center.y + " " + center.z + " " + radius;
    }

    /*public SphereEntity(Vec3 center, float radius) {
        this = new SphereEntity(center,radius,Color.GREEN);
    }*/

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
    public String getFormula() {
        return formula;
    }

    @Override
    public void setFormula(String string) {
        formula = string;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color newColor) {
        color = newColor;
        mf1.setColor(color);
        mf2.setColor(color);
        mf3.setColor(color);
        mf4.setColor(color);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean stillExists() {
        return exists;
    }

    @Override
    public void delete() {
        exists = false;
    }
}
