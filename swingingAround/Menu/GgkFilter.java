package swingingAround.Menu;

import java.io.File;

/**
 * FileFilter ami arra szolgál, hogy a fájl mentő/nyitó
 * dialogus ablakban megjelnő fájlokat szűrje úgy, hogy
 * csak .ggk és mappák jelenjenek meg bennük.
 */
public class GgkFilter extends javax.swing.filechooser.FileFilter {
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
}
