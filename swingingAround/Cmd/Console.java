package swingingAround.Cmd;

import swingingAround.Entities.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Parancssor osztály. String-ben érkező parancsokat értelmez, és hajt végre.
 * <p>
 *     Minden parancs a konfigurációs osztályon változtat.
 *
 *     A parancs-értelmezés úgy zajlik, hogy a beérkező String
 *     "szavakra" lesz bontva, és ezek után a parancs első
 *     szavát kulcsként használva egy HashMap-ben a
 *     visszakapott osztály maga a futtatandó parancs lesz,
 *     ami ezek után le is fut.
 *
 *     Ez az osztály kiterjeszti a JTextField-et, így ezt
 *     hozzá lehet adni egy adott JFrame-hez. A szövegmező
 *     tartalma módosítható, és enter hatására a tartalmát
 *     parancsként kezelve az osztály megpróbálja végrehajtani
 *     azt. Ezek után a szövegmező tartalmát kiüríti - üres
 *     String-re állítja.
 * </p>
 */
public class Console extends JTextField {

    /**
     * A jelenlegi, még nem értelmezett parancs.
     */
    private String currentCmd = "";
    /**
     * Statikus HashMap, melyben a kulcs a parancs első szava, a visszatérő érték pedig a futtatandó parancs.
     */
    private static HashMap<String, Command> commands = null;
    /**
     * A konfigurációs osztály, melynek az értékein a parancsmezőbe beírt, és lefuttatott parancsok változtatnak.
     */
    private Config currentConf;

    /**
     * Az osztály konstruktora - alap értékeket állít be a megfelelő futáshoz.
     * <p>
     *     Létrehozza a "commands" nevű HashMap-et, ha az még nincs inicializálva,
     *     és hozzá adja a parancsokat a megfelelő kulcsszavakkal.
     *
     *     Ezenkívűl beállítja a szövegmező méreteit, font méretét, és listener-jeit.
     * </p>
     */
    public Console() {
        if (commands == null)
            commands = new HashMap<String, Command>();

        this.setEditable(true);
        this.setColumns(30);
        Font font = this.getFont();
        font = font.deriveFont((float) 20);
        this.setFont(font);
        this.addKeyListener(new CmdListener());

        //Config stuff
        //--------------------------------------------------------------------------------------------------------------
        currentConf = new Config();
        currentConf.setGraphingEnabled(true);
        currentConf.setGraphingTrianglesFilled(false);
        currentConf.setNumberOfTrianglesX(50);
        currentConf.setNumberOfTrianglesY(50);
        currentConf.setNumberOfTrianglesZ(50);
        currentConf.setStepSize(1.0);

        //HashMap stuff
        //--------------------------------------------------------------------------------------------------------------
        if (!commands.containsKey("x") && !(commands.containsKey("x"))) {
            commands.put("x", new FunctionCommand());
            commands.put("X", new FunctionCommand());
        }

        if (!commands.containsKey("y") && !commands.containsKey("Y")) {
            commands.put("y", new FunctionCommand());
            commands.put("Y", new FunctionCommand());
        }

        if (!commands.containsKey("z") && !commands.containsKey("Z")) {
            commands.put("z", new FunctionCommand());
            commands.put("Z", new FunctionCommand());
        }

        if (!commands.containsKey("sphere") && !commands.containsKey("Sphere")) {
            commands.put("sphere", new SphereCommand());
            commands.put("Sphere", new SphereCommand());
        }

        if (!commands.containsKey("line") && !commands.containsKey("Line")) {
            commands.put("Line", new LineCommand());
            commands.put("line", new LineCommand());
        }

        if (!commands.containsKey("point") && !commands.containsKey("point")) {
            commands.put("point", new PointCommand());
            commands.put("Point", new PointCommand());
        }

        if (!commands.containsKey("set") && !commands.containsKey("Set")) {
            commands.put("set", new SetCommand());
            commands.put("Set", new SetCommand());
        }
    }

    public void setConfig(Config conf) {currentConf = conf;}
    public Config getConfig() {return currentConf;}

    /**
     * Statikusan végrehajtható parancsértelmezés
     * @param conf a konfigurációs osztály, melyben parancs hatására az adat változhat.
     * @param formula a parancs amit értelmezni kell.
     * @param entityId törlendő entitás Id-ja - lehet null - erre akkor van szükség, ha egy létező entitás formula-ja változik.
     * @return a megváltozott adatokkal rendelkező konfigurációs osztály
     */
    public static Config tryToExecChangedFormula(Config conf, String formula, String entityId) {
        String formulaCopy = formula;

        if (entityId != null)
            conf.removeEntity(entityId);

        if (formula.contains(":"))
            formula = formula.substring(0,formula.indexOf(":"));

        formula = formula.replace("="," ");
        String[] cmd = formula.split("\\s+");

        if (commands.containsKey(cmd[0])) {
            Command cm = commands.get(cmd[0]);
            conf = cm.execute(cmd, conf);
            conf = staticCheckNameChange(conf, formulaCopy);
        }

        return conf;
    }

    /**
     * Adott parancs végrehajtása az osztály saját konfigurációs osztályán.
     * @param formula a végrehajtandó parancs
     */
    public void tryToExecFormula(String formula) {
        currentConf = tryToExecChangedFormula(currentConf, formula, null);
    }

    /**
     * A szövegmezőben található szöveg parancsként való végrehajtása az osztály saját konfigurációs osztályán.
     */
    public void tryToExecCmd() {
        currentCmd = this.getText();
        this.setText("");

        System.out.println(currentCmd);
        tryToExecFormula(currentCmd);
    }

    /**
     * Ha a parancsban szerepel ":", akkor az után következő szöveg rész lesz az utolsóként felvett entitás neve.
     * @param conf Melyik konfigurációs osztályt használja a függvény
     * @param formula parancs amiben szerepelhet ":"
     * @return a megváltozott konfigurációs osztály
     */
    public static Config staticCheckNameChange(Config conf, String formula) {
        String[] cmd = formula.split(":");
        if (cmd.length >= 2) {
            String name = cmd[1];
            name = name.replace(" ","");
            ArrayList<Entity> entities = conf.getEntities();

            Entity nameChange = entities.get(entities.size()-1);
            nameChange.setName(name);

            conf.getEntities().set(entities.size()-1,nameChange);
        }

        return conf;
    }

    /**
     * Az osztály saját konfigurációs osztályában végzi el a névváltást, feltéve, hogy a parancsban szerepel ":"
     * @param formula a parancs amiben ":" szerepelhet.
     */
    public void checkNameChange(String formula) {
        currentConf = staticCheckNameChange(currentConf, formula);
    }

    /**
     * Azt figyeli, hogy a szövegmezőbe való írás folytán nyomodótt-e enter.
     * Ha az enter meg lett nyomva, akkor a szövegmezőben lévő szöveget
     * próbálja meg parancsként lefutattatni.
     */
    private class CmdListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                tryToExecCmd();
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }

}
