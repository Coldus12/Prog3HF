package swingingAround;

import swingingAround.Entity;
import swingingAround.Mathfunction;

import java.awt.*;

public class LineEntity extends Entity {
    private static int number;

    private String name;
    private String formula;
    private Color color;
    private Vec3 direction, p1;
    private boolean exists;

    public LineEntity(Vec3 p1, Vec3 p2, Color c) {
        number++;
        exists = true;
        color = c;

        direction = new Vec3(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
        name = "line" + number;
        this.p1 = p1;
        this.formula = "line " + p1.x + " " + p1.y + " " + p1.z + " " + p2.x + " " + p2.y + " " + p2.z;
    }

    public Vec3 calcPartOfLine(float lambda) {
        return new Vec3(p1.x + lambda * direction.x, p1.y + lambda * direction.y, p1.z + lambda * direction.z);
    }

    public Vec3 getDirection() {return direction;}

    @Override
    public Mathfunction[] getMathfunctions() {
        return new Mathfunction[0];
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