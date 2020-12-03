package swingingAround.Menu;

import swingingAround.Cmd.Config;
import swingingAround.Cmd.Console;
import swingingAround.Entities.Entity;
import swingingAround.ThreeD.Vec3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * .ggk fájl megnyitására szolgáló menü osztály.
 */
public class GeoOpenFileMenu extends JMenuItem {

    /**
     * Az ablak amiből ki lehet választani melyik fájlt szeretnénk megnyitni.
     */
    private JFileChooser chooser;
    /**
     * A kiválasztott, megnyitandó fájl.
     */
    private File chosenFile;
    /**
     * A konfigurációs osztály, amibe bele olvassuk a fájlból az adatokat.
     */
    private Config currentConf;
    private boolean openedAFile = false;

    public GeoOpenFileMenu() {
        this.setText("Open");

        chooser = new JFileChooser();
        this.addActionListener(new Openlistener());
    }

    /**
     * Visszaadja az új konfigurációs osztályt, ha lett fájl megnyitva, egyébként azt adja vissza, amit kapott.
     * @param conf konfigurációs osztály amit visszaad, ha nem nyitottak meg fájlt.
     * @return a frissen betöltött konfigurációs osztály, vagy az amit kapott.
     */
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

    /**
     * A menü megnyomását figyelő privát osztály
     * <p>
     *     Ha megnyomták a menü gombot, akkor megnyit egy új
     *     fájl kiválasztó ablakot, ahonnan ki lehet választani
     *     a megnyitandó fájlt. Ugyanakkor ebben az ablakban csak
     *     mappák, és ".ggk"-ra végződő fájlok jelennek meg.
     *
     *     Ha sikerült az ablakban fájlt választani, akkor
     *     annak a tartalmát beolvassa egy konfigurációs osztályba.
     * </p>
     */
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

    /**
     * Beolvassa egy megnyitott fájl tartalmát egy új konfigurációs osztályba.
     * @param openedFile a megnyitásra kiválasztott fájl
     * @return egy új a fájlban szereplő adatokkal feltöltött konfigurációs osztály.
     */
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
            System.err.println("Returning new, empty config.");
            ex.printStackTrace();
            conf = new Config();
            conf.setFreshlyLoadedFromFile(true);
            return conf;
        }

        return conf;
    }

}
