package swingingAround;

import java.awt.*;

public class LineEntity extends Entity {
    private static int number;

    private Vec3 direction, p1;

    public LineEntity(Vec3 p1, Vec3 p2, Color c) {
        number++;
        setColor(c);

        direction = new Vec3(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
        setId("line"+number);
        setName("line" + number);
        this.p1 = p1;
        setFormula("line " + p1.x + " " + p1.y + " " + p1.z + " " + p2.x + " " + p2.y + " " + p2.z);
    }

    public Vec3 calcPartOfLine(float lambda) {
        return new Vec3(p1.x + lambda * direction.x, p1.y + lambda * direction.y, p1.z + lambda * direction.z);
    }

    public Vec3 getDirection() {return direction;}

    @Override
    public Mathfunction[] getMathfunctions() {
        return new Mathfunction[0];
    }

}