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
import java.nio.file.Path;
import java.util.ArrayList;

public class GeoSaveAsMenu extends JMenuItem {

    JFileChooser chooser;
    File chosenFile;
    Config currentConf;

    public GeoSaveAsMenu(Config conf) {
        this.setText("Save as");
        currentConf = conf;

        chooser = new JFileChooser();
        this.addActionListener(new SaveListener());
    }

    public void updateConf(Config newerConf) {currentConf = newerConf;}

    private class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chooser.setFileFilter(new GgkFilter());

            int returnVal = chooser.showSaveDialog(GeoSaveAsMenu.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                chosenFile = chooser.getSelectedFile();

                SaveGGK save = new SaveGGK(chosenFile, currentConf);
            }
        }
    }

}
