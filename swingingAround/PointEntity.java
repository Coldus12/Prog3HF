package swingingAround;

import java.awt.*;

public class PointEntity extends Entity {
    private static int number;

    private Vec3 point;

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
