package swingingAround;

import java.awt.*;

public abstract class Mathfunction {
    private String name = "NoName";
    private Color color = Color.GREEN;

    enum Axis {x, y, z, notDefined}
    private Axis currentAxis = Axis.notDefined;

    public Axis getCurrentAxis() {return currentAxis;}
    public void setCurrentAxis(Axis axis) {currentAxis = axis;}

    public String getName() {return name;}
    public Color getColor() {return color;}
    public void setColor(Color c) {color = c;}

    public abstract float exec3DFunction(float x, float y, float z);
}
