package swingingAround;

import java.awt.*;

public abstract class Entity {

    private String formula;
    private Color color;
    private String name;
    private String id;
    private boolean exists = true;

    public abstract Mathfunction[] getMathfunctions();

    public String getFormula() {
        return formula;
    }

    public void setFormula(String string) {
        formula = string;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        color = newColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String newId) {
        id = newId;
    }

    public String getId() {
        return id;
    }

    public boolean stillExists() {
        return exists;
    }

    public void delete() {
        exists = false;
    }
}
