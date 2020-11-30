package swingingAround.Cmd;

import swingingAround.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Config {
    private boolean isGraphingEnabled = true;
    private boolean graphingTrianglesFilled = false;

    private int numberOfTrianglesX = 50;
    private int numberOfTrianglesZ = 50;
    private int numberOfTrianglesY = 50;

    private Vec3 camPos;

    private double stepSize = 1;

    //private ArrayList<Mathfunction> functions;
    private ArrayList<Entity> entities;
    private ArrayList<LineEntity> lines;
    private ArrayList<PointEntity> points;

    public Config() {
        entities = new ArrayList<Entity>();
        lines = new ArrayList<LineEntity>();
        points = new ArrayList<PointEntity>();
    }

    public Config(Config conf) {
        this.isGraphingEnabled = conf.isGraphingEnabled();
        this.graphingTrianglesFilled = conf.graphingTrianglesFilled;
        this.numberOfTrianglesX = conf.numberOfTrianglesX;
        this.numberOfTrianglesZ = conf.numberOfTrianglesZ;
        this.numberOfTrianglesY = conf.numberOfTrianglesY;

        this.entities = conf.getEntities();
        this.lines = conf.getLines();
        this.points = conf.getPoints();
        this.stepSize = conf.getStepSize();
    }

    //Getters
    //------------------------------------------------------------------------------------------------------------------
    public boolean isGraphingEnabled() {
        return isGraphingEnabled;
    }

    public boolean getGraphingTrianglesFilled() {
        return graphingTrianglesFilled;
    }

    public int getNumberOfTrianglesX() {
        return numberOfTrianglesX;
    }

    public int getNumberOfTrianglesZ() {
        return numberOfTrianglesZ;
    }

    public int getNumberOfTrianglesY() { return  numberOfTrianglesY; }

    public ArrayList<Entity> getEntities() { return entities; }

    public ArrayList<LineEntity> getLines() { return lines; }

    public ArrayList<PointEntity> getPoints() {
        return points;
    }

    public Vec3 getCamPos() { return camPos; }

    public double getStepSize() { return stepSize; }

    //Setters
    //------------------------------------------------------------------------------------------------------------------
    public void setGraphingEnabled(boolean graphingEnabled) {
        isGraphingEnabled = graphingEnabled;
    }

    public void setGraphingTrianglesFilled(boolean graphingTrianglesFilled) {
        this.graphingTrianglesFilled = graphingTrianglesFilled;
    }

    public void setNumberOfTrianglesX(int numberOfTrianglesX) {
        this.numberOfTrianglesX = numberOfTrianglesX;
    }

    public void setNumberOfTrianglesZ(int numberOfTrianglesZ) {
        this.numberOfTrianglesZ = numberOfTrianglesZ;
    }

    public void setNumberOfTrianglesY(int numberOfTrianglesY) {this.numberOfTrianglesY = numberOfTrianglesY;}

    public void setCamPos(Vec3 pos) {camPos = pos;}

    public void setStepSize(double newStepSize) { stepSize = newStepSize; }

    //Others
    //------------------------------------------------------------------------------------------------------------------
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(String entityName) {
        lines.removeIf(line -> (line.getName().equals(entityName)));
        points.removeIf(point -> (point.getName().equals(entityName)));
        entities.removeIf(n -> (n.getName().equals(entityName)));
    }

    public ArrayList<Mathfunction> getMathfunctions() {
        ArrayList<Mathfunction> mfs = new ArrayList<Mathfunction>();
        for (Entity entity : entities) {
            Collections.addAll(mfs, entity.getMathfunctions());
        }

        return mfs;
    }

    public void updateEntity(Entity entity) {
        lines.removeIf(line -> !line.stillExists());
        points.removeIf(point -> !point.stillExists());

        for (int i = 0; i <  entities.size(); i++) {
            if (entities.get(i).getName().equals(entity.getName())) {
                /*if (entity.stillExists())
                    entities.set(i,entity);
                else {
                    entities.remove(i);
                    return;
                }*/

                if (!entity.stillExists()) {
                    entities.remove(i);
                    return;
                }
            }
        }
    }

    public void addLine(LineEntity line) {
        lines.add(line);
        entities.add(line);
    }

    public void addPoint(PointEntity point) {
        points.add(point);
        entities.add(point);
    }

}
