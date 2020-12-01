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

            SaveGGK save = new SaveGGK(saveFile, currentConf);
        }
    }
}
