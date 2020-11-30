package swingingAround;

import swingingAround.Cmd.Config;
import swingingAround.Cmd.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class EntityPanel extends JPanel {

    JComboBox<String> colorBox;
    JButton deleteButton;
    Entity entity;
    JTextField formula;
    Color color = Color.GREEN;

    Config currentConf;
    boolean confChanged = false;

    public EntityPanel(Entity entity, Config conf) {
        String[] colors = {"Cyan","Blue","Magenta","Pink","Red","Orange","Yellow","Green","White","Lightgray","Gray","Darkgray","Black"};
        colorBox = new JComboBox<String>(colors);
        colorBox.setSelectedItem("Green");
        colorBox.addActionListener(new ColorListener());
        this.setLayout(new FlowLayout());
        this.add(colorBox);
        this.setBackground(Color.GRAY);
        this.setSize(50,200);
        colorBox.setBackground(Color.GREEN);

        this.entity = entity;
        formula = new JTextField();
        formula.setSize(100,20);
        formula.setPreferredSize(new Dimension(100,20));
        formula.setText(entity.getFormula());
        Font font = formula.getFont();
        font = font.deriveFont((float) 14);
        formula.setFont(font);

        this.add(formula);

        deleteButton = new JButton("Delete");
        deleteButton.setSize(40,40);
        deleteButton.addActionListener(new ButtonListener());
        this.add(deleteButton);

        formula.addKeyListener(new FormulaListener());

        currentConf = conf;
    }

    public void updateEntityByFormula() {
        confChanged = true;

        currentConf = Console.tryToExecChangedFormula(currentConf,formula.getText(), entity.getId());
        ArrayList<Entity> entities = currentConf.getEntities();
        entity = entities.get(entities.size()-1);
        entity.setColor(color);
    }

    public void setConfig(Config conf) {
        currentConf = conf;
    }

    public Config getConf() {
        confChanged = false;
        return currentConf;
    }

    public Entity getEntity() {
        return entity;
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //System.out.println("Clicked fuck ye" + entity.getFormula() + " " + entity.getColor());
            entity.delete();
        }
    }

    public boolean hasEntity() {
        return entity.stillExists();
    }

    private class ColorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switch ((String) colorBox.getSelectedItem()) {
                case "Cyan":
                    color = Color.CYAN;
                    break;
                case "Blue":
                    color = Color.BLUE;
                    break;
                case "Magenta":
                    color = Color.MAGENTA;
                    break;
                case "Pink":
                    color = Color.PINK;
                    break;
                case "Red":
                    color = Color.RED;
                    break;
                case "Orange":
                    color = Color.ORANGE;
                    break;
                case "Yellow":
                    color = Color.YELLOW;
                    break;
                case "Green":
                    color = Color.GREEN;
                    break;
                case "White":
                    color = Color.WHITE;
                    break;
                case "Lightgray":
                    color = Color.LIGHT_GRAY;
                    break;
                case "Gray":
                    color = Color.GRAY;
                    break;
                case "Darkgray":
                    color = Color.DARK_GRAY;
                    break;
                case "Black":
                    color = Color.BLACK;
                    break;
                default:
                    break;
            }

            colorBox.setBackground(color);
            entity.setColor(color);
        }
    }

    private class FormulaListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                updateEntityByFormula();
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }

}
