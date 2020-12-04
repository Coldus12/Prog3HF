package swingingAround;

import swingingAround.Cmd.Config;
import swingingAround.Cmd.Console;
import swingingAround.Entities.Entity;
import swingingAround.Entities.LineEntity;
import swingingAround.Entities.Mathfunction;
import swingingAround.Entities.PointEntity;
import swingingAround.Menu.GeoOpenFileMenu;
import swingingAround.Menu.GeoSaveAsMenu;
import swingingAround.Menu.GeoStandardSaveMenu;
import swingingAround.ThreeD.Camera;
import swingingAround.ThreeD.RotMatrices;
import swingingAround.ThreeD.Triangle;
import swingingAround.ThreeD.Vec3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * A program ablaka.
 */
public class GeoFrame extends JFrame {

    /**
     * Az a panel, amelyen a teret megjeleníti a program
     */
    private final PaintPanel renderPanel;
    /**
     * Egy panel, melyhez hozzáadódnak az entitás panelek, és
     * mely megjeleníti őket.
     */
    private final JPanel configPanel;
    /**
     * A kamera, amelynek a szemszögéből megjelenik a tér.
     */
    private final Camera cam;
    /**
     * A fenti parancssor - feladata a beírt parancsok feldolgozása,
     * és a konfiguráció módosítása.
     */
    private final Console cmdLine;
    /**
     * A konfiguráció.
     */
    private Config conf;
    /**
     * Fut-e a program. Amíg ez igaz, addig ciklikusan kerülnek frissítésre,
     * megjelenítésre a dolgok. Ha nem igaz, akkor a program kilép.
     */
    private boolean running = true;
    /**
     * Entitás panelekből álló lista.
     */
    private ArrayList<EntityPanel> entityPanels;

    /**
     * A memóriában tárolt, megjelenítendő entitások Mathfunction-jei
     * <p>
     *     Egy függvény adott pontjait elég egyszer kiszámolni addig, amíg
     *     Minden Mathfunction-höz tartozik egy mátrix, a calcMat()
     *     függvény ezt a mátrixot feltölti a függvény
     * </p>
     */
    private ArrayList<Mathfunction> mfsInMemory = null;

    /**
     * A program menüje.
     */
    private final JMenuBar menuBar;
    /**
     * A saveAsMenu - mellyel ki lehet választani, hogy hova, milyen néven szeretnénk
     * a fájlt elmentení.
     */
    private final GeoSaveAsMenu saveAsMenu;
    /**
     * standardSaveMenu - abba a mappába ahonnan a programot indították menti el
     * az adatokat "klon" + szám + ".ggk" néven.
     */
    private final GeoStandardSaveMenu standardSaveMenu;
    /**
     * openFileMenu - ennek segítségével ki lehet választani melyik fájl tartalmát
     * szeretnénk a konfigurációba betölteni.
     */
    private final GeoOpenFileMenu openFileMenu;


    /**
     * Az ablak konstruktora. A program rögtön elindul, amint a konstruktora meghívódik.
     * <p>
     *     Beállítja az ablak, és a hozzá tartozó különböző panelek tulajdonságait,
     *     beállítja az ablak layout-ját, inicializálja a kamerát, létrehozza a menüt,
     *     és utána egy végtelen ciklusba kerül, melyben parancsot értelmez, entitásokat
     *     rajzol entitásokat frissít, stb. egészen addig, amíg a programot be nem
     *     zárjuk.
     * </p>
     */
    public GeoFrame() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setEnabled(true);
        this.setBounds(0,0,1350,775);
        this.setResizable(false);
        this.setLayout(new GridBagLayout());
        this.setTitle("GeoCopy");

        entityPanels = new ArrayList<EntityPanel>();

        renderPanel = new PaintPanel(1080,720,108,72);
        renderPanel.setVisible(true);
        renderPanel.setEnabled(true);

        configPanel = new JPanel();
        configPanel.setPreferredSize(new Dimension(270,720));
        configPanel.setSize(270,720);
        configPanel.setMinimumSize(new Dimension(270,720));
        configPanel.setSize(new Dimension(270,720));
        configPanel.setLayout(new BoxLayout(configPanel,BoxLayout.Y_AXIS));

        cmdLine = new Console();

        //Camera
        //--------------------------------------------------------------------------------------------------------------
        cam = new Camera(540,360,new Vec3(0,0,0),90 * Math.PI/180.0);
        renderPanel.setScreen(cam.getScreen());

        //GridBagLayout stuff
        //--------------------------------------------------------------------------------------------------------------
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridheight = 3;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 20;
        this.add(configPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(cmdLine, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.weightx = 1;
        c.weighty = 1;
        this.add(renderPanel, c);

        this.addKeyListener(new GeoListener());
        this.addMouseListener(new ClickListener());

        //Config
        //--------------------------------------------------------------------------------------------------------------
        conf = new Config();

        //Menu
        //--------------------------------------------------------------------------------------------------------------
        menuBar = new JMenuBar();
        saveAsMenu = new GeoSaveAsMenu(conf);
        standardSaveMenu = new GeoStandardSaveMenu(conf);
        openFileMenu = new GeoOpenFileMenu();
        menuBar.add(standardSaveMenu);
        menuBar.add(saveAsMenu);
        menuBar.add(openFileMenu);
        this.setJMenuBar(menuBar);

        //Updating UI
        //--------------------------------------------------------------------------------------------------------------
        this.setVisible(true);

        //Main loop?
        //--------------------------------------------------------------------------------------------------------------
        while (running) {
            try {
                long time = System.currentTimeMillis();
                conf = cmdLine.getConfig();
                loadingOpenedConfig();
                updateConfigPanel();
                updateEntities();
                standardSaveMenu.updateConf(conf);
                saveAsMenu.updateConf(conf);
                draw();
                long time2 = System.currentTimeMillis();
                long delta = time2 - time;
                if (delta <= 50)
                    sleep(50 - delta);
                else
                    sleep(50);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Frissíti a konfigurációs panel tartalmát.
     * <p>
     *     A konfigurációs panel az az a panel, melyben az
     *     entitáspanelek kerülnek megjelenítésre, melyekben
     *     láthatóak az entitások tulajdonságai, és azok
     *     módosíthatóak is.
     *
     *     Ebben a függvényben ez a panel frissül, ami annyit
     *     tesz, hogy ha a konfigurációs fájlban szerepel olya
     *     entitás melyhez még nem tartozik entitáspanel, akkor
     *     ahhoz/azokhoz létrehoz entitáspanelt.
     *
     *     Vagy ha a konfiguráció frissen lett betöltve valami
     *     fájlból, akkor törli az összes meglévő entitás panelt,
     *     és újakat hoz létre az új entitásoknak.
     * </p>
     */
    private void updateConfigPanel() {
        ArrayList<Entity> entities = conf.getEntities();

        if (conf.isConfigFreshlyLoaded()) {
            entityPanels = new ArrayList<EntityPanel>();
            configPanel.removeAll();

            for (int i = 0; i < entities.size(); i++) {
                entityPanels.add(new EntityPanel(entities.get(i),conf));
                entityPanels.get(i).setAlignmentX(Component.CENTER_ALIGNMENT);
                configPanel.add(entityPanels.get(i));
            }
            conf.setFreshlyLoadedFromFile(false);
        } else {
            if (entities.size() > entityPanels.size()) {
                entityPanels.add(new EntityPanel(entities.get(entities.size()-1),conf));
                entityPanels.get(entityPanels.size()-1).setAlignmentX(Component.CENTER_ALIGNMENT);
                configPanel.add(entityPanels.get(entityPanels.size()-1));
            }
        }

        configPanel.repaint();
        configPanel.updateUI();
    }

    /**
     * Frissíti az összes entitást, ami annyit tesz, hogy
     * ha az entitás trölésre került, azaz az exists boolean-ja
     * hamis, akkor anak törlődik az entitáspanele, és ő maga is
     * törlődik a konfigurációból.
     */
    private void updateEntities() {
        conf.updateEntity();
        for (int i = 0; i < entityPanels.size(); i++) {
            EntityPanel current = entityPanels.get(i);

            if (!current.hasEntity()) {
                if (current.confChanged)
                    conf = current.getConf();
                else
                    current.setConfig(conf);

                configPanel.remove(current);
                entityPanels.remove(i);
                configPanel.updateUI();
            }
        }

        cmdLine.setConfig(conf);
    }

    /**
     * Ha fájlból új konfigurációt töltöttek be, akkor frissíti a konfigurációt.
     */
    private void loadingOpenedConfig() {
        conf.setCamPos(cam.getPos());
        conf = openFileMenu.getConfig(conf);

        if (!conf.getCamPos().equals(cam.getPos())) {
            cam.setPos(conf.getCamPos());
        }
    }

    /**
     * Kirajzolja az entitásokat.
     * <p>
     *     A függvényeket a(z) mfsInMemory listában tárolja a program. Mindegyik függvényhez
     *     tartozik egy a konfigurációnak (numberOfTriangleX, numberOfTrianglesY, numberOfTrianglesZ)
     *     megfelelő magas, és széles mátrix, melyben az origo-t véve középpontul "stepsize"
     *     lépésmérettel már ki vannak számítva előre a függvény értékei. Így azokat csak el
     *     kell kérni a megfelelő pont(ok)ban a mátrixtól és meg kell őket jeleníteni. Ha a
     *     konfiguráióban valami változás történik, akkor ezeket a mátrixokat újra számolja
     *     a program.
     *
     *     A pontokat simán csak kirajzolja a program ha kitudja.
     *
     *     Ami az egyeneseket illeti, azok a úgy kerülnek kirajzolásra, hogy megnézzük, hogy
     *     a kamera pozíciója milyen lambdahoz "van közel" majd a program ettől mérve -500,500
     *     intervallumon rajzolja ki őket.
     * </p>
     */
    private void draw() {
        renderPanel.setScreen(cam.getScreen());
        cam.screen.fillRectangle(0,0,cam.screen.getWidth(), cam.screen.getHeight(), new Color(30,30,30));
        ArrayList<LineEntity> lines = conf.getLines();

        float stepSize = (float) conf.getStepSize();

        if ((mfsInMemory == null) || conf.hasConfigChanged()) {
            mfsInMemory = conf.getMathfunctions();
            Vec3 origo = new Vec3(0,0,0);
            for (Mathfunction mf: mfsInMemory) {
                switch (mf.getCurrentAxis()) {
                    case x:
                        mf.calcMat(origo,conf.getNumberOfTrianglesY(),conf.getNumberOfTrianglesZ(),conf.getStepSize());
                        break;
                    case y:
                        mf.calcMat(origo,conf.getNumberOfTrianglesX(),conf.getNumberOfTrianglesZ(),conf.getStepSize());
                        break;
                    case z:
                        mf.calcMat(origo,conf.getNumberOfTrianglesX(),conf.getNumberOfTrianglesY(),conf.getStepSize());
                        break;
                }
            }
        }

        double xInLoop = conf.getNumberOfTrianglesX()/2.0 * stepSize;
        double yInLoop = conf.getNumberOfTrianglesY()/2.0 * stepSize;
        double zInLoop = conf.getNumberOfTrianglesZ()/2.0 * stepSize;

        if (conf.isGraphingEnabled()) {

            //Points
            //----------------------------------------------------------------------------------------------------------
            ArrayList<PointEntity> points = conf.getPoints();
            for (PointEntity p: points) {
                cam.render3DPoint(p.getPoint(), p.getColor());
            }

            //Lines
            //----------------------------------------------------------------------------------------------------------
            for (LineEntity line: lines) {
                double lambda = (cam.getPos().x) / line.getDirection().x - (conf.getNumberOfTrianglesX());

                for (int i = (int) lambda - 500; i < (int) lambda + 500; i++) {
                    Vec3 p1 = line.calcPartOfLine(i);
                    Vec3 p2 = line.calcPartOfLine(i+1);

                    cam.render3DLine(p1, p2, line.getColor());
                }
            }

            //X
            //----------------------------------------------------------------------------------------------------------
            float currentX = (float) (0 - xInLoop);
            float currentY = (float) (0 - yInLoop);
            float currentZ = (float) (0 - zInLoop);

            for (int j = 0; j < 50; j++) {
                currentY+=stepSize;
                for (int k = 0; k < 50; k++) {
                    currentZ+=stepSize;
                    for (Mathfunction mf: mfsInMemory) {

                        if (mf.getCurrentAxis() == Mathfunction.Axis.x) {
                            double x1 = mf.getMatValueAt(j,k);
                            double x2 = mf.getMatValueAt(j+1,k);
                            double x3 = mf.getMatValueAt(j+1,k+1);
                            double x4 = mf.getMatValueAt(j,k+1);

                            Vec3 v1 = new Vec3(x1,currentY,currentZ);
                            Vec3 v2 = new Vec3(x2,currentY+stepSize,currentZ);
                            Vec3 v3 = new Vec3(x3,currentY+stepSize,currentZ+stepSize);
                            Vec3 v4 = new Vec3(x4,currentY,currentZ+stepSize);

                            if (conf.getGraphingTrianglesFilled()) {
                                cam.render3DFilledTriangle(new Triangle(v1,v2,v3,mf.getColor()));
                                cam.render3DFilledTriangle(new Triangle(v1,v4,v3,mf.getColor()));
                            } else {
                                cam.render3DTriangle(new Triangle(v1,v2,v3,mf.getColor()));
                                cam.render3DTriangle(new Triangle(v1,v4,v3,mf.getColor()));
                            }
                        }

                    }
                }
                currentZ = (float) (0 - zInLoop);
            }

            //Y
            //----------------------------------------------------------------------------------------------------------
            currentX = (float) (0 - xInLoop);
            currentY = (float) (0 - yInLoop);
            currentZ = (float) (0 - zInLoop);


            for (int i = 0; i < conf.getNumberOfTrianglesX(); i++) {
                currentX+=stepSize;
                for (int k = 0; k < conf.getNumberOfTrianglesZ(); k++) {
                    currentZ+=stepSize;
                    for (Mathfunction mf: mfsInMemory) {

                        if (mf.getCurrentAxis() == Mathfunction.Axis.y) {
                            double y1 = mf.getMatValueAt(i,k);
                            double y2 = mf.getMatValueAt(i+1,k);
                            double y3 = mf.getMatValueAt(i+1,k+1);
                            double y4 = mf.getMatValueAt(i,k+1);

                            Vec3 v1 = new Vec3(currentX,y1,currentZ);
                            Vec3 v2 = new Vec3(currentX+stepSize,y2,currentZ);
                            Vec3 v3 = new Vec3(currentX+stepSize,y3,currentZ+stepSize);
                            Vec3 v4 = new Vec3(currentX,y4,currentZ+stepSize);

                            if (conf.getGraphingTrianglesFilled()) {
                                cam.render3DFilledTriangle(new Triangle(v1,v2,v3,mf.getColor()));
                                cam.render3DFilledTriangle(new Triangle(v1,v4,v3,mf.getColor()));
                            } else {
                                cam.render3DTriangle(new Triangle(v1,v2,v3,mf.getColor()));
                                cam.render3DTriangle(new Triangle(v1,v4,v3,mf.getColor()));
                            }
                        }

                    }
                }
                currentZ = (float) (0 - zInLoop);
            }

            //Z
            //----------------------------------------------------------------------------------------------------------
            currentX = (float) (0 - xInLoop);
            currentY = (float) (0 - yInLoop);
            currentZ = (float) (0 - zInLoop);


            for (int i = 0; i < conf.getNumberOfTrianglesX(); i++) {
                currentX+=stepSize;
                for (int j = 0; j < conf.getNumberOfTrianglesY(); j++) {
                    currentY+=stepSize;
                    for (Mathfunction mf: mfsInMemory) {

                        if (mf.getCurrentAxis() == Mathfunction.Axis.z) {
                            double z1 = mf.getMatValueAt(i,j);
                            double z2 = mf.getMatValueAt(i+1,j);
                            double z3 = mf.getMatValueAt(i+1,j+1);
                            double z4 = mf.getMatValueAt(i,j+1);

                            Vec3 v1 = new Vec3(currentX,currentY,z1);
                            Vec3 v2 = new Vec3(currentX+stepSize,currentY,z2);
                            Vec3 v3 = new Vec3(currentX+stepSize,currentY+stepSize,z3);
                            Vec3 v4 = new Vec3(currentX,currentY+stepSize,z4);

                            if (conf.getGraphingTrianglesFilled()) {
                                cam.render3DFilledTriangle(new Triangle(v1,v2,v3,mf.getColor()));
                                cam.render3DFilledTriangle(new Triangle(v1,v4,v3,mf.getColor()));
                            } else {
                                cam.render3DTriangle(new Triangle(v1,v2,v3,mf.getColor()));
                                cam.render3DTriangle(new Triangle(v1,v4,v3,mf.getColor()));
                            }
                        }

                    }
                }
                currentY = (float) (0 - yInLoop);
            }
        }

        renderPanel.setScreen(cam.getScreen());
        renderPanel.repaint();

        cmdLine.setConfig(conf);
    }

    /**
     * Az ablakra való kattintásra figyelő MouseListener.
     * <p>
     *     Erre azért van szükség, mert egy JTextField elveszi
     *     a fókuszt az ablaktól, így az nem képes a leütött
     *     billentyűkre reagálni. Így ha rákattintunk az ablakra,
     *     azzal visszaadjuk neki a fókuszt, és tud reagálni a
     *     billenytű leütésekre.
     * </p>
     */
    private class ClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            focusRequest();
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            focusRequest();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            focusRequest();
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    /**
     * Billentyű leütéseket figyeli - leginkább a kamera irányítása miatt.
     * <p>
     *     Az Escape lenyomása után a program kilép.
     *     W-vel a kamera egyet megy előre a z tengely mentén.
     *     S-vel a kamera egyet megy hátra a z tengely mentén.
     *     A-vel a kamera egyet megy balra az x tengely mentén.
     *     D-vel a kamera egyet megy jobbra az x tengely mentén.
     *     U-val a kamera egyet megy felfelé az y tengely mentén.
     *     J-vel a kamera egyet megy lefelé az y tengely mentén.
     *
     *     Q-val az y tengely körül fordul a kamera egy fokkal
     *     E-vel az y tengely körül fordul a kamera minusz egy fokkal
     *
     *     F-val az x tengely körül fordul a kamera egy fokkal
     *     R-vel az x tengely körül fordul a kamera minusz egy fokkal
     *
     *     Y-al a z tengely körül fordul a kamera egy fokkal
     *     X-el a z tengely körül fordul a kamera minusz egy fokkal
     * </p>
     */
    private class GeoListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    running = false;
                    getFrame().dispose();
                    break;

                case KeyEvent.VK_W:
                    cam.getPos().moveZByDelta(1);
                    break;
                case KeyEvent.VK_S:
                    cam.getPos().moveZByDelta(-1);
                    break;
                case KeyEvent.VK_A:
                    cam.getPos().moveXbyDelta(-1);
                    break;
                case KeyEvent.VK_D:
                    cam.getPos().moveXbyDelta(1);
                    break;
                case KeyEvent.VK_U:
                    cam.getPos().moveYByDelta(1);
                    break;
                case KeyEvent.VK_J:
                    cam.getPos().moveYByDelta(-1);
                    break;

                //Camera rotations
                //------------------------------------------------------------------------------------------------------
                case KeyEvent.VK_Q:
                    cam.setBase(cam.getBase().multiplyByMatrix(RotMatrices.rotateYByDegree(1)));
                    break;

                case KeyEvent.VK_E:
                    cam.setBase(cam.getBase().multiplyByMatrix(RotMatrices.rotateYByDegree(-1)));
                    break;

                case KeyEvent.VK_F:
                    cam.setBase(cam.getBase().multiplyByMatrix(RotMatrices.rotateXByDegree(1)));
                    break;

                case KeyEvent.VK_R:
                    cam.setBase(cam.getBase().multiplyByMatrix(RotMatrices.rotateXByDegree(-1)));
                    break;

                case KeyEvent.VK_Y:
                    cam.setBase(cam.getBase().multiplyByMatrix(RotMatrices.rotateZByDegree(1)));
                    break;

                case KeyEvent.VK_X:
                    cam.setBase(cam.getBase().multiplyByMatrix(RotMatrices.rotateZByDegree(-1)));
                    break;

                default:
                    System.out.println(cam.getPos());
                    renderPanel.repaint();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }

    /**
     * Átadja a fókuszt az ablaknak.
     */
    private void focusRequest() {
        this.requestFocus();
    }

    /**
     * Visszaadja ezt az ablakot.
     * @return Ez az ablak.
     */
    private JFrame getFrame() {
        return this;
    }
}
