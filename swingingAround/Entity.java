package swingingAround;

import java.awt.*;

public abstract class Entity {

    public abstract Mathfunction[] getMathfunctions();

    public abstract String getFormula();
    public abstract void setFormula(String string);

    public abstract Color getColor();
    public abstract void setColor(Color newColor);

    public abstract String getName();
    public abstract void setName(String name);

}
