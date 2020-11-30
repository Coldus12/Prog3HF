package swingingAround.Cmd;

import swingingAround.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Console extends JTextField {

    public ArrayList<String> previousCmds;
    public String currentCmd = "";
    static private HashMap<String, Command> commands;
    private Config currentConf;

    public Console() {
        previousCmds = new ArrayList<String>();
        commands = new HashMap<String, Command>();

        this.setEditable(true);
        this.setColumns(30);
        Font font = this.getFont();
        font = font.deriveFont((float) 20);
        this.setFont(font);
        this.addKeyListener(new cmdListener());

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
        commands.put("x", new FunctionCommand());
        commands.put("X", new FunctionCommand());

        commands.put("y", new FunctionCommand());
        commands.put("Y", new FunctionCommand());

        commands.put("z", new FunctionCommand());
        commands.put("Z", new FunctionCommand());

        commands.put("sphere", new SphereCommand());
        commands.put("Sphere", new SphereCommand());

        commands.put("Line",new LineCommand());
        commands.put("line",new LineCommand());

        commands.put("point", new PointCommand());
        commands.put("Point", new PointCommand());

        commands.put("set",new SetSizeCommand());
        commands.put("Set",new SetSizeCommand());
    }

    public void setConfig(Config conf) {currentConf = conf;}
    public Config getConfig() {return currentConf;}

    public void tryToExecCmd() {
        currentCmd = this.getText();
        this.setText("");

        System.out.println(currentCmd);

        String formulaCopy = currentCmd;

        if (currentCmd.contains(":"))
            currentCmd = currentCmd.substring(0,currentCmd.indexOf(":"));

        currentCmd = currentCmd.replace("="," ");

        String[] cmd = currentCmd.split("\\s+");
        if (commands.containsKey(cmd[0])) {
            Command cm = commands.get(cmd[0]);
            currentConf = cm.execute(cmd, currentConf);
            checkNameChange(formulaCopy);
        }
    }

    public void tryToExecFormula(String formula) {
        String formulaCopy = formula;

        if (formula.contains(":"))
            formula = formula.substring(0,formula.indexOf(":"));

        formula = formula.replace("="," ");
        String[] cmd = formula.split("\\s+");

        if (commands.containsKey(cmd[0])) {
            Command cm = commands.get(cmd[0]);
            currentConf = cm.execute(cmd, currentConf);
            checkNameChange(formulaCopy);
        }
    }

    public static Config tryToExecChangedFormula(Config conf, String formula, String entityId) {
        String formulaCopy = formula;

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

    public void checkNameChange(String formula) {
        currentConf = staticCheckNameChange(currentConf, formula);
    }

    private class cmdListener implements KeyListener {
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
