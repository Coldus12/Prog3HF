package swingingAround.Menu;

import swingingAround.Cmd.Config;
import swingingAround.Entity;
import swingingAround.Vec3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveGGK {

    public SaveGGK(File saveFile, Config conf) {
        try {
            ArrayList<Entity> entities = conf.getEntities();
            FileWriter fw = new FileWriter(saveFile);
            BufferedWriter bf = new BufferedWriter(fw);
            Vec3 cam = conf.getCamPos();
            bf.write(cam.x + " " + cam.y + " " + cam.z);

            for (Entity n: entities)
                bf.write("\n"+n.getFormula() + " : " + n.getName());

            bf.write("\nset width " + conf.getNumberOfTrianglesX());
            bf.write("\nset height " + conf.getNumberOfTrianglesY());
            bf.write("\nset length " + conf.getNumberOfTrianglesZ());
            bf.write("\nset stepSize " + conf.getStepSize());
            if (conf.getGraphingTrianglesFilled())
                bf.write("\nset filled true");
            else
                bf.write("\nset filled false");

            bf.close();
            fw.close();
        } catch (IOException ex) {
            System.err.println("Something went wrong while saving. File path+name: " + saveFile.getPath());
            ex.printStackTrace();
        }
    }

}
