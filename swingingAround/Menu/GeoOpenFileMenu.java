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

    //public void updateConf(Config newerConf) {currentConf = newerConf;}
    public Config getConfig(Config conf) {
        if (openedAFile) {

            ArrayList<Entity> entities = currentConf.getEntities();
            for (Entity n: entities) {
                System.out.println(n.getFormula());
            }

            System.out.println("here we are mate");
            openedAFile = false;
            return currentConf;
        } else
            return conf;
    }

    private class Openlistener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.getName().endsWith(".ggk"))
                        return true;

                    if (file.isDirectory())
                        return true;
                    return false;
                }

                @Override
                public String getDescription() {
                    return null;
                }
            });

            int returnVal = chooser.showOpenDialog(GeoOpenFileMenu.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                chosenFile = chooser.getSelectedFile();
                String line = "";

                try {
                    if (chosenFile.getName().endsWith(".ggk")) {
                        Console console = new Console();
                        FileReader fr = new FileReader(chosenFile);
                        BufferedReader bf = new BufferedReader(fr);

                        currentConf = new Config();
                        currentConf.setGraphingTrianglesFilled(false);
                        currentConf.setGraphingEnabled(true);
                        currentConf.setNumberOfTrianglesX(50);
                        currentConf.setNumberOfTrianglesY(50);
                        currentConf.setNumberOfTrianglesZ(50);

                        console.setConfig(currentConf);

                        line = bf.readLine();
                        String[] coords = line.split(" ");
                        double posX = Double.parseDouble(coords[0]);
                        double posY = Double.parseDouble(coords[1]);
                        double posZ = Double.parseDouble(coords[2]);
                        Vec3 camPos = new Vec3(posX,posY,posZ);

                        currentConf.setCamPos(camPos);
                        //System.out.println("CurrentConf" + currentConf.getCamPos());

                        while ((line = bf.readLine()) != null) {
                            console.tryToExecFormula(line);
                        }

                        currentConf = console.getConfig();
                        currentConf.setFreshlyLoadedFromFile(true);
                        openedAFile = true;

                        System.out.println("done reading a file");
                        bf.close();
                        fr.close();
                    }
                } catch (IOException ex) {ex.printStackTrace();}
            }
        }
    }

}
