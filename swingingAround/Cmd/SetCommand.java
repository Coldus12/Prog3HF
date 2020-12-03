package swingingAround.Cmd;

/**
 * A konfigurációs osztály változóinak értékét lehet ezekkel a parancsokkal megváltoztatni.
 * <p>
 *     A "set width/height/length x" parancs beállítja, hogy egy adott tengely mentén
 *     hány háromszöget rajzoljon ki a program. A "height" az y tengely, a "width" az x tengely,
 *     míg a "length" a z tengely mentén állítja ezt be.
 *
 *     A "set stepSize x" parancs azt változtatja meg, hogy a tengelyek mentén mekkora
 *     egységenként legyen új háromszög kirajzolva.
 *
 *     A "set filled true/false" parancstól függ, hogy a program teli háromszögeket rajzol ki,
 *     vagy csak az oldalait rajzolja ki a háromszögeknek.
 * </p>
 */
public class SetCommand implements Command {
    @Override
    public Config execute(String[] cmd, Config conf) {

        if (cmd.length >= 3) {
            double size = 0;

            if (!(cmd[2].equals("true") || cmd[2].equals("false")))
                size = Double.parseDouble(cmd[2]);

            if (cmd[1].equals("width"))
                conf.setNumberOfTrianglesX((int) size);
            else if (cmd[1].equals("height"))
                conf.setNumberOfTrianglesY((int) size);
            else if (cmd[1].equals("length"))
                conf.setNumberOfTrianglesZ((int) size);
            else if (cmd[1].equals("stepSize"))
                conf.setStepSize(size);
            else if (cmd[1].equals("filled")) {
                if (cmd[2].equals("true")) {
                    conf.setGraphingTrianglesFilled(true);
                } else if (cmd[2].equals("false")) {
                    conf.setGraphingTrianglesFilled(false);
                }
            }
        }

        return conf;
    }
}
