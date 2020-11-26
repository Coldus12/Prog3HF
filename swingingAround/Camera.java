package swingingAround;

import java.awt.*;

public class Camera {
    private Vec3 pos;
    public Screen screen;
    private double distanceToScreen;
    private double fov;

    private double degree = Math.PI/180;
    private dMatrix base;

    public Camera(int width, int height, Vec3 pos, Vec3 direction, double FOV) {
        this.pos = pos;

        screen = new Screen(width,height);
        distanceToScreen = width/(2.0 * Math.tan((FOV/2)));
        fov = FOV;
        base = new dMatrix(3,3);

        base.setValueAt(0,0,1);
        base.setValueAt(0,1,0);
        base.setValueAt(0,2,0);

        base.setValueAt(1,0,0);
        base.setValueAt(1,1,1);
        base.setValueAt(1,2,0);

        base.setValueAt(2,0,0);
        base.setValueAt(2,1,0);
        base.setValueAt(2,2,1);
    }

    //Setters
    //------------------------------------------------------------------------------------------------------------------
    public void setFOV(double FOV) {
        distanceToScreen = screen.getWidth()/(2.0 * Math.tan((FOV/2)));
        fov = FOV;
    }

    public void setPos(Vec3 pos) {
        this.pos = pos;
    }

    public void setBase(dMatrix newBase) {
        if ((newBase.getWidth() == 3) && (newBase.getHeight() == 3)) {
            base = newBase;
        }
    }

    //Getters
    //------------------------------------------------------------------------------------------------------------------
    public Vec3 getPos() {
        return pos;
    }

    public double getDistanceToScreen() {
        return distanceToScreen;
    }

    public double getFOV() {
        return fov;
    }

    public dMatrix getBase() {
        return base;
    }

    //Get the camera view
    //------------------------------------------------------------------------------------------------------------------
    public Screen getScreen() {
        return screen;
    }

    //Helper functions
    //------------------------------------------------------------------------------------------------------------------
    private Vec3 vec3ToScreenCoordinates(Vec3 vec) {
        Vec3 ret = new Vec3(vec.x,vec.y,vec.z);

        double relativY = distanceToScreen * ((ret.y - pos.y)/(ret.z - pos.z));
        double relativX = distanceToScreen * ((ret.x - pos.x)/(ret.z - pos.z));

        ret.y = ((double) screen.getHeight() / 2) - relativY;
        ret.x = ((double) screen.getWidth() / 2) - relativX;

        if (ret.z - pos.z <= 5) {
            ret.z = -1;
        } else {
            ret.z = 0;
        }

        return ret;
    }

    //Main functions
    //------------------------------------------------------------------------------------------------------------------
    public void render3DPoint(Vec3 vec) {
        dMatrix inverse = base;
        inverse = inverse.multiplyByNumber(distanceToScreen);
        inverse = inverse.getInverse();

        Vec3 v1b = vec.multiplyVec3ByMatrix(inverse);

        Vec3 camPos = getPos().multiplyVec3ByMatrix(inverse);

        v1b = new Vec3(v1b.x - camPos.x, v1b.y - camPos.y, v1b.z - camPos.z);

        if (v1b.z > 0) {
            v1b.multiplyByNumber((distanceToScreen)/(v1b.z));
            v1b.x += screen.getWidth()/2.0;
            v1b.y = screen.getHeight()/2.0 - v1b.y;

            try {
                screen.drawPoint((int) v1b.x, (int) v1b.y,3, Color.GREEN);
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public void render3DTriangle(Triangle tri) {
        dMatrix inverse = base;
        inverse = inverse.multiplyByNumber(distanceToScreen);
        inverse = inverse.getInverse();

        Vec3 v1b = tri.v1.multiplyVec3ByMatrix(inverse);
        Vec3 v2b = tri.v2.multiplyVec3ByMatrix(inverse);
        Vec3 v3b = tri.v3.multiplyVec3ByMatrix(inverse);

        Vec3 camPos = getPos().multiplyVec3ByMatrix(inverse);
        //Vec3 camPos = getPos();

        v1b = new Vec3(v1b.x - camPos.x, v1b.y - camPos.y, v1b.z - camPos.z);
        v2b = new Vec3(v2b.x - camPos.x, v2b.y - camPos.y, v2b.z - camPos.z);
        v3b = new Vec3(v3b.x - camPos.x, v3b.y - camPos.y, v3b.z - camPos.z);

        if ((v1b.z > 0) && (v2b.z  > 0) && (v3b.z > 0)) {
            v1b.multiplyByNumber((distanceToScreen)/(v1b.z));
            v2b.multiplyByNumber((distanceToScreen)/(v2b.z));
            v3b.multiplyByNumber((distanceToScreen)/(v3b.z));

            v1b.x += screen.getWidth()/2.0;
            v2b.x += screen.getWidth()/2.0;
            v3b.x += screen.getWidth()/2.0;

            v1b.y = screen.getHeight()/2.0 - v1b.y;
            v2b.y = screen.getHeight()/2.0 - v2b.y;
            v3b.y = screen.getHeight()/2.0 - v3b.y;

            try {
                //screen.fillTriangle((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, (int) v3b.x, (int) v3b.y, new Color(90,90,9));
                screen.drawTriangle((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, (int) v3b.x, (int) v3b.y, tri.c);
                //screen.fillTriangle((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, (int) v3b.x, (int) v3b.y, tri.c);
                //screen.setColorAt((int) v1b.x, (int) v1b.y,Color.CYAN);
                //screen.setColorAt((int) v2b.x, (int) v2b.y,Color.CYAN);
                //screen.setColorAt((int) v3b.x, (int) v3b.y,Color.CYAN);
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public void render3DFilledTriangle(Triangle tri) {
        dMatrix inverse = base;
        inverse = inverse.multiplyByNumber(distanceToScreen);
        inverse = inverse.getInverse();

        Vec3 v1b = tri.v1.multiplyVec3ByMatrix(inverse);
        Vec3 v2b = tri.v2.multiplyVec3ByMatrix(inverse);
        Vec3 v3b = tri.v3.multiplyVec3ByMatrix(inverse);

        Vec3 camPos = getPos().multiplyVec3ByMatrix(inverse);

        v1b = new Vec3(v1b.x - camPos.x, v1b.y - camPos.y, v1b.z - camPos.z);
        v2b = new Vec3(v2b.x - camPos.x, v2b.y - camPos.y, v2b.z - camPos.z);
        v3b = new Vec3(v3b.x - camPos.x, v3b.y - camPos.y, v3b.z - camPos.z);

        if ((v1b.z > 0) && (v2b.z  > 0) && (v3b.z > 0)) {
            v1b.multiplyByNumber((distanceToScreen)/(v1b.z));
            v2b.multiplyByNumber((distanceToScreen)/(v2b.z));
            v3b.multiplyByNumber((distanceToScreen)/(v3b.z));

            v1b.x += screen.getWidth()/2.0;
            v2b.x += screen.getWidth()/2.0;
            v3b.x += screen.getWidth()/2.0;

            v1b.y = screen.getHeight()/2.0 - v1b.y;
            v2b.y = screen.getHeight()/2.0 - v2b.y;
            v3b.y = screen.getHeight()/2.0 - v3b.y;

            try {
                //screen.fillTriangle((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, (int) v3b.x, (int) v3b.y, new Color(90,90,9));
                //screen.drawTriangle((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, (int) v3b.x, (int) v3b.y, tri.c);
                screen.fillTriangle((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, (int) v3b.x, (int) v3b.y, tri.c);
                //screen.setColorAt((int) v1b.x, (int) v1b.y,Color.CYAN);
                //screen.setColorAt((int) v2b.x, (int) v2b.y,Color.CYAN);
                //screen.setColorAt((int) v3b.x, (int) v3b.y,Color.CYAN);
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public void render3DLine(Vec3 p1, Vec3 p2, Color c) {
        dMatrix inverse = base;
        inverse = inverse.multiplyByNumber(distanceToScreen);
        inverse = inverse.getInverse();

        Vec3 v1b = p1.multiplyVec3ByMatrix(inverse);
        Vec3 v2b = p2.multiplyVec3ByMatrix(inverse);

        Vec3 camPos = getPos().multiplyVec3ByMatrix(inverse);
        //Vec3 camPos = getPos();

        v1b = new Vec3(v1b.x - camPos.x, v1b.y - camPos.y, v1b.z - camPos.z);
        v2b = new Vec3(v2b.x - camPos.x, v2b.y - camPos.y, v2b.z - camPos.z);

        if ((v1b.z > 0) && (v2b.z  > 0)) {
            v1b.multiplyByNumber((distanceToScreen)/(v1b.z));
            v2b.multiplyByNumber((distanceToScreen)/(v2b.z));

            v1b.x += screen.getWidth()/2.0;
            v2b.x += screen.getWidth()/2.0;

            v1b.y = screen.getHeight()/2.0 - v1b.y;
            v2b.y = screen.getHeight()/2.0 - v2b.y;

            try {
                //screen.fillTriangle((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, (int) v3b.x, (int) v3b.y, new Color(90,90,9));
                //screen.drawTriangle((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, (int) v3b.x, (int) v3b.y, tri.c);
                //screen.fillTriangle((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, (int) v3b.x, (int) v3b.y, tri.c);
                //screen.setColorAt((int) v1b.x, (int) v1b.y,Color.CYAN);
                //screen.setColorAt((int) v2b.x, (int) v2b.y,Color.CYAN);
                //screen.setColorAt((int) v3b.x, (int) v3b.y,Color.CYAN);
                screen.drawLine((int) v1b.x, (int) v1b.y, (int) v2b.x, (int) v2b.y, c);
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }
}
