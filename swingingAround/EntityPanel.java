package swingingAround;

import swingingAround.Cmd.Config;
import swingingAround.Cmd.Console;
import swingingAround.Entities.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Entitások információinak megjelenítésére és módosítására fenntartott módosított JPanel
 */
public class EntityPanel extends JPanel {

    /**
     * Színválasztásra szolgáló JComboBox
     * <p>
     *     13 különböző szín neve közül lehet választani.
     *     A kulcsszó a név. Ez egy String-et váltogat,
     *     és van egy ActionListener-je, ami az action
     *     hatására egy switch segítségével megváltoztatja
     *     az adott entitás színét.
     * </p>
     */
    private JComboBox<String> colorBox;
    /**
     * Az entitás törlésére szolgáló gomb
     */
    private JButton deleteButton;
    /**
     * Az entitás aminek az információi megjelenítendők, és módosíthatók.
     */
    private Entity entity;
    /**
     * Az entitás képlete/függvénye/parancsa, ami módosítható, melynek hatására akár az egész entitás lecserélődhet.
     */
    private JTextField formula;
    /**
     * Az entitás színe
     */
    private Color color = Color.GREEN;

    /**
     * Egy konfiguráció, ami folyamatosan frissül.
     * <p>
     *     Erre csak azért van szükség, mert ha ennek a
     *     formula nevezetű JTextField-jén érkezik parancs,
     *     ami megváltoztat valamit a konfiguráción, akkor azt
     *     valahogy jelezni kell. Ez úgy történik, hogy a
     *     confChanged boolean true-ra változik, és ekkor
     *     ennek az osztálynak a getConfig-je a legfrissebb
     *     konfigurációt adja vissza, amiben már a módosítás
     *     is szerepel.
     * </p>
     */
    private Config currentConf;
    /**
     * Ha ez igaz, akkor ennek az osztálynak a legfrissebb konfigurációja kerül használatba.
     */
    boolean confChanged = false;

    /**
     * EntityPanel konstruktora.
     * <p>
     *     Létrehozza a szükséges GUI elemeket, és beállítja azok
     *     tulajdonságait.
     * </p>
     * @param entity a megejelenítendő és módosíthatü entitás
     * @param conf a jelenlegi konfiguráció
     */
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
        formula.setText(entity.getFormula() + " : " + entity.getName());
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

    /**
     * A formula JTextField-ben lévő parancs/formula alapján frissíti a panel
     * saját entitását, és a konfigurációt is.
     */
    public void updateEntityByFormula() {
        confChanged = true;

        currentConf = Console.tryToExecChangedFormula(currentConf,formula.getText(), entity.getId());
        ArrayList<Entity> entities = currentConf.getEntities();
        entity = entities.get(entities.size()-1);
        entity.setColor(color);
    }

    /**
     * Beállít egy új konfigurációt
     * @param conf az új konfiguráció
     */
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

    /**
     * A törlés gombot figyelő ActionListener. Ha a gombot megnyomják, akkor az entitás törlésre kerül.
     */
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            entity.delete();
        }
    }

    public boolean hasEntity() {
        return entity.stillExists();
    }

    /**
     * Egy actionListener, ami a JComboBox megváltozására
     * az éppen kiválasztott String alapján megváltoztatja
     * az entitás színét.
     */
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

    /**
     * KeyListener, ami azt figyeli, hogy nyomtak-e entert a formula-t
     * kiíró, és értelmező JTextField-en.
     * <p>
     *     Ha enter-t nyomtak, akkor a formulát, ami a JTextField-ben
     *     szerepel megpróbálja parancsként feldolgozni.
     * </p>
     */
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
