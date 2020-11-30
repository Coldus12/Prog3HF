package swingingAround;

import java.awt.*;

public class PointEntity extends Entity {
    private static int number;

    private String formula;
    private String name;
    private Color color;
    private Vec3 point;
    private boolean exists = true;

    public PointEntity(Vec3 point, Color c) {
        this.point = point;
        this.color = c;
        number++;
        name = "Point"+number;

        formula = "Point " + point.x + " " + point.y + " " + point.z;
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
