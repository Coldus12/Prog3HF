package swingingAround.Menu;

import swingingAround.Cmd.Config;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * .ggk fájl mentésére szolgáló menü osztály.
 * <p>
 *     A fájlt abba a könyvtárba menti el, ahonnan a programot
 *     elindítottuk. A mentett fájl neve "klon"+szám+".ggk" lesz.
 *     Ahol a szám 0, ha még nincs ilyen nevű fájl, ha pedig van,
 *     akkor addig nő a szám értéke, amíg nincs ilyen nevű fájl a
 *     könyvtárban.
 * </p>
 */
public class GeoStandardSaveMenu extends JMenuItem {
    /**
     * A konfiguráció aminek tartalmát menteni szeretnénk.
     */
    private Config currentConf;

    public GeoStandardSaveMenu(Config conf) {
        this.setText("Save");
        currentConf = conf;

        this.addActionListener(new SaveListener());
    }

    /**
     * Ezzel lehet frissíteni a mentendő konfigurációt.
     * @param newerConf az újabb mentendő konfiguráció
     */
    public void updateConf(Config newerConf) {currentConf = newerConf;}

    /**
     * A menü megnyomását követően megpróbálja a fájlt elmenteni
     * a megfelelő könyvtárba, a megfelelő névvel.
     */
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

            SaveGGK.saveConfigToFile(saveFile, currentConf);
        }
    }
}
