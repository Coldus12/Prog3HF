package swingingAround.Cmd;

import swingingAround.Entity;
import swingingAround.LineEntity;
import swingingAround.Mathfunction;
import swingingAround.Vec3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Config {
    private boolean isGraphingEnabled;
    private boolean graphingTrianglesFilled;

    private int numberOfTrianglesX;
    private int numberOfTrianglesZ;
    private int numberOfTrianglesY;

    private Vec3 camPos;

    //private ArrayList<Mathfunction> functions;
    private ArrayList<Entity> entities;
    private ArrayList<LineEntity> lines;

    public Config() {
        entities = new ArrayList<Entity>();
        lines = new ArrayList<LineEntity>();
    }

    public Config(Config conf) {
        this.isGraphingEnabled = conf.isGraphingEnabled();
        this.graphingTrianglesFilled = conf.graphingTrianglesFilled;
        this.numberOfTrianglesX = conf.numberOfTrianglesX;
        this.numberOfTrianglesZ = conf.numberOfTrianglesZ;
        this.numberOfTrianglesY = conf.numberOfTrianglesY;

        this.entities = conf.getEntities();
        this.lines = conf.getLines();
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

    public Vec3 getCamPos() {return camPos;}

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

    //Others
    //------------------------------------------------------------------------------------------------------------------
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(String entityName) {
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
        for (LineEntity line: lines) {
            System.out.println(line.stillExists());
            if (!line.stillExists())
                lines.remove(line);
        }

        for (int i = 0; i <  entities.size(); i++) {
            if (entities.get(i).getName().equals(entity.getName())) {
                if (entity.stillExists())
                    entities.set(i,entity);
                else {
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

}
