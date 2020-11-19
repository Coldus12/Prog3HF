package swingingAround;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntityPanel extends JPanel {

    JComboBox<String> colorBox;
    JButton deleteButton;
    Entity entity;
    JTextField formula;

    public EntityPanel(Entity entity) {
        String[] colors = {"Cyan","Blue","Magenta","Pink","Red","Orange","Yellow","Green","White","Lightgray","Gray","Darkgray","Black"};
        colorBox = new JComboBox<String>(colors);
        colorBox.addActionListener(new ColorListener());
        this.setLayout(new FlowLayout());
        this.add(colorBox);
        this.setBackground(Color.GRAY);
        colorBox.setBackground(Color.GREEN);

        this.entity = entity;
        formula = new JTextField();
        formula.setText(entity.getFormula());
        Font font = formula.getFont();
        font = font.deriveFont((float) 20);
        formula.setFont(font);

        this.add(formula);

        deleteButton = new JButton("Delete");
        deleteButton.setSize(40,40);
        this.add(deleteButton);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    private class ColorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Color color = Color.GREEN;
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
        }
    }

}
