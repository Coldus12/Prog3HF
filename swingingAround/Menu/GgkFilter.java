package swingingAround.Menu;

import java.io.File;
import java.io.FileFilter;

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
