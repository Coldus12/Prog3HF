package swingingAround.Menu;

import swingingAround.Cmd.Config;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * .ggk fájl mentésére szolgáló menü osztály, ha szeretnénk
 * kiválasztani hova, és milyen néven legyen mentve a fájl.
 */
public class GeoSaveAsMenu extends JMenuItem {
    /**
     * Az ablak amiből ki lehet választani hova szeretnénk menteni a fájlt, és milyen néven.
     */
    private JFileChooser chooser;
    /**
     * A fájl amibe menteni szeretnénk.
     */
    private File chosenFile;
    /**
     * A konfiguráció aminek tartalmát menteni szeretnénk.
     */
    private Config currentConf;

    public GeoSaveAsMenu(Config conf) {
        this.setText("Save as");
        currentConf = conf;

        chooser = new JFileChooser();
        this.addActionListener(new SaveListener());
    }

    /**
     * Ezzel lehet frissíteni a mentendő konfigurációt.
     * @param newerConf az újabb mentendő konfiguráció
     */
    public void updateConf(Config newerConf) {currentConf = newerConf;}

    /**
     * A menü megnyomását figyelő privát osztály
     * <p>
     *     Ha megnyomták a menü gombot, akkor megnyit egy új
     *     fájl kiválasztó ablakot, ahonnan ki lehet választani,
     *     hogy hova szeretnénk menteni a konfiguráció, és milyen
     *     néven.
     * </p>
     */
    private class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            chooser.setFileFilter(new GgkFilter());

            int returnVal = chooser.showSaveDialog(GeoSaveAsMenu.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                chosenFile = chooser.getSelectedFile();

                SaveGGK.saveConfigToFile(chosenFile,currentConf);
            }
        }
    }

}
