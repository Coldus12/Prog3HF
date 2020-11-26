package swingingAround.Cmd;

import swingingAround.GeoFrame;
import swingingAround.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Console extends JTextField {

    public ArrayList<String> previousCmds;
    public String currentCmd = "";
    private HashMap<String, Command> commands;
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
    }

    public void setConfig(Config conf) {currentConf = conf;}
    public Config getConfig() {return currentConf;}

    public void tryToExecCmd() {
        currentCmd = this.getText();
        this.setText("");

        System.out.println(currentCmd);

        String[] cmd = currentCmd.split("\\s+");
        if (commands.containsKey(cmd[0])) {
            Command cm = commands.get(cmd[0]);
            currentConf = cm.execute(cmd, currentConf);
        }
    }

    public void tryToExecFormula(String formula) {
        String[] cmd = formula.split("\\s+");
        if (commands.containsKey(cmd[0])) {
            Command cm = commands.get(cmd[0]);
            currentConf = cm.execute(cmd, currentConf);
        }
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
