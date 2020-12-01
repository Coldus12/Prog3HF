package swingingAround.Menu;

import swingingAround.Cmd.Config;
import swingingAround.Cmd.Console;
import swingingAround.Entity;
import swingingAround.Vec3;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class GeoOpenFileMenu extends JMenuItem {

    JFileChooser chooser;
    File chosenFile;
    Config currentConf;
    boolean openedAFile = false;

    public GeoOpenFileMenu() {
        this.setText("Open");

        chooser = new JFileChooser();
        this.addActionListener(new Openlistener());
    }

    public Config getConfig(Config conf) {
        if (openedAFile) {

            ArrayList<Entity> entities = currentConf.getEntities();
            for (Entity n: entities) {
                System.out.println(n.getFormula());
            }

            openedAFile = false;
            return currentConf;
        } else
            return conf;
    }

    private class Openlistener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chooser.setFileFilter(new GgkFilter());

            int returnVal = chooser.showOpenDialog(GeoOpenFileMenu.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                chosenFile = chooser.getSelectedFile();

                currentConf = openGgkFile(chosenFile);
                if (currentConf != null)
                    openedAFile = true;
            }
        }
    }

    public static Config openGgkFile(File openedFile) {
        String line = "";
        Config conf = null;

        try {
            if (openedFile.getName().endsWith(".ggk")) {
                Console console = new Console();
                FileReader fr = new FileReader(openedFile);
                BufferedReader bf = new BufferedReader(fr);

                conf = new Config();
                conf.setGraphingTrianglesFilled(false);
                conf.setGraphingEnabled(true);
                conf.setNumberOfTrianglesX(50);
                conf.setNumberOfTrianglesY(50);
                conf.setNumberOfTrianglesZ(50);

                console.setConfig(conf);

                line = bf.readLine();
                String[] coords = line.split(" ");
                double posX = Double.parseDouble(coords[0]);
                double posY = Double.parseDouble(coords[1]);
                double posZ = Double.parseDouble(coords[2]);
                Vec3 camPos = new Vec3(posX,posY,posZ);

                conf.setCamPos(camPos);

                while ((line = bf.readLine()) != null) {
                    console.tryToExecFormula(line);
                }

                conf = console.getConfig();
                conf.setFreshlyLoadedFromFile(true);

                System.out.println("done reading a file");
                bf.close();
                fr.close();
            }
        } catch (IOException ex) {
            System.err.println("Something went wrong while opening file: " + openedFile.getName() + "\nPath: " + openedFile.getPath());
            ex.printStackTrace();
        }

        return conf;
    }

}
