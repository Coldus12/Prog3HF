package swingingAround.Menu;

import swingingAround.Cmd.Config;
import swingingAround.Entity;
import swingingAround.Vec3;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GeoStandardSaveMenu extends JMenuItem {
    Config currentConf;

    public GeoStandardSaveMenu(Config conf) {
        this.setText("Save");
        currentConf = conf;

        this.addActionListener(new SaveListener());
    }

    public void updateConf(Config newerConf) {currentConf = newerConf;}

    private class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            File wd = new File(System.getProperty("user.dir"));

            int i = 0;
            File saveFile = new File(wd.getPath() + File.separatorChar + "klon" + i + ".ggk");
            while (saveFile.isFile()) {
                i++;
                saveFile = new File(wd.getPath() + File.separatorChar + "klon" + i + ".ggk");
            }

            ArrayList<Entity> entities = currentConf.getEntities();

            try {
                FileWriter fw = new FileWriter(saveFile);
                BufferedWriter bf = new BufferedWriter(fw);
                Vec3 cam = currentConf.getCamPos();
                bf.write(cam.x + " " + cam.y + " " + cam.z);

                for (Entity n: entities)
                    bf.write("\n"+n.getFormula());

                bf.close();
                fw.close();
            } catch (IOException ex) {ex.printStackTrace();}
        }
    }
}
