package swingingAround;

import swingingAround.Cmd.Config;
import swingingAround.Cmd.Console;
import swingingAround.Menu.GeoOpenFileMenu;
import swingingAround.Menu.GeoSaveAsMenu;
import swingingAround.Menu.GeoStandardSaveMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class GeoFrame extends JFrame {

    private final PaintPanel renderPanel;
    private final JPanel configPanel;
    private final Camera cam;
    private final Console cmdLine;
    private Config conf;
    private boolean running = true;
    private ArrayList<EntityPanel> entityPanels;
    private final JMenuBar menuBar;
    private final GeoSaveAsMenu saveAsMenu;
    private final GeoStandardSaveMenu standardSaveMenu;
    private final GeoOpenFileMenu openFileMenu;


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
        cam = new Camera(540,360,new Vec3(0,0,0),new Vec3(0,0,1),90 * Math.PI/180.0);
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

    private void updateEntities() {
        for (int i = 0; i < entityPanels.size(); i++) {
            EntityPanel current = entityPanels.get(i);

            conf.updateEntity(current.getEntity());
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

    private void loadingOpenedConfig() {
        conf.setCamPos(cam.getPos());
        conf = openFileMenu.getConfig(conf);

        if (!conf.getCamPos().equals(cam.getPos())) {
            cam.setPos(conf.getCamPos());
        }
    }

    private void draw() {
        ArrayList<Mathfunction> mfs = conf.getMathfunctions();
        renderPanel.setScreen(cam.getScreen());
        cam.screen.fillRectangle(0,0,cam.screen.getWidth(), cam.screen.getHeight(), new Color(30,30,30));
        ArrayList<LineEntity> lines = conf.getLines();

        float stepSize = (float) conf.getStepSize();

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
            //Kezdo lambda
            for (LineEntity line: lines) {
                double lambda = (cam.getPos().x) / line.getDirection().x - (conf.getNumberOfTrianglesX());

                for (int i = (int) lambda; i < (int) (lambda + (conf.getNumberOfTrianglesX())); i++) {
                    Vec3 p1 = line.calcPartOfLine(i);
                    Vec3 p2 = line.calcPartOfLine(i+1);

                    cam.render3DLine(p1, p2, line.getColor());
                }
            }

            //X
            //--------------------------------------------------------------------------------------------------
            float currentX = (float) (cam.getPos().x - xInLoop);
            float currentY = (float) (cam.getPos().y - yInLoop);
            float currentZ = (float) (cam.getPos().z - zInLoop);

            for (int j = 0; j < conf.getNumberOfTrianglesY(); j++) {
                currentY += stepSize;
                for (int k = 0; k < conf.getNumberOfTrianglesZ(); k++) {
                    currentZ += stepSize;

                    for (Mathfunction mf: mfs) {

                        if (mf.getCurrentAxis() == Mathfunction.Axis.x) {
                            double x1 = mf.exec3DFunction(0, currentY, currentZ);
                            double x2 = mf.exec3DFunction(0, currentY + stepSize, currentZ);
                            double x3 = mf.exec3DFunction(0, currentY + stepSize, currentZ + stepSize);
                            double x4 = mf.exec3DFunction(0, currentY, currentZ + stepSize);

                            if (conf.getGraphingTrianglesFilled()) {
                                cam.render3DFilledTriangle(new Triangle(new Vec3(x1,currentY,currentZ),new Vec3(x2,currentY+stepSize,currentZ),new Vec3(x3,currentY+stepSize,currentZ+stepSize),mf.getColor()));
                                cam.render3DFilledTriangle(new Triangle(new Vec3(x1,currentY,currentZ),new Vec3(x4,currentY,currentZ+stepSize),new Vec3(x3,currentY+stepSize,currentZ+stepSize),mf.getColor()));
                            } else {
                                cam.render3DTriangle(new Triangle(new Vec3(x1,currentY,currentZ),new Vec3(x2,currentY+stepSize,currentZ),new Vec3(x3,currentY+stepSize,currentZ+stepSize),mf.getColor()));
                                cam.render3DTriangle(new Triangle(new Vec3(x1,currentY,currentZ),new Vec3(x4,currentY,currentZ+stepSize),new Vec3(x3,currentY+stepSize,currentZ+stepSize),mf.getColor()));
                            }
                        }

                    }
                }
                currentZ = (float) (cam.getPos().z - zInLoop);
            }

            //Y
            //--------------------------------------------------------------------------------------------------
            currentX = (float) (cam.getPos().x - xInLoop);
            currentY = (float) (cam.getPos().y - yInLoop);
            currentZ = (float) (cam.getPos().z - zInLoop);

            for (int i = 0; i < conf.getNumberOfTrianglesX(); i++) {
                currentX += stepSize;
                for (int k = 0; k < conf.getNumberOfTrianglesZ(); k++) {
                    currentZ += stepSize;

                    for (Mathfunction mf: mfs) {

                        if (mf.getCurrentAxis() == Mathfunction.Axis.y) {
                            double y1 = mf.exec3DFunction(currentX,0,currentZ);
                            double y2 = mf.exec3DFunction(currentX + stepSize,0,currentZ);
                            double y3 = mf.exec3DFunction(currentX + stepSize,0,currentZ + stepSize);
                            double y4 = mf.exec3DFunction(currentX,0,currentZ + stepSize);

                            if (conf.getGraphingTrianglesFilled()) {
                                cam.render3DFilledTriangle(new Triangle(new Vec3(currentX,y1,currentZ),new Vec3(currentX+stepSize,y2,currentZ),new Vec3(currentX+stepSize,y3,currentZ+stepSize),mf.getColor()));
                                cam.render3DFilledTriangle(new Triangle(new Vec3(currentX,y1,currentZ),new Vec3(currentX,y4,currentZ+stepSize),new Vec3(currentX+stepSize,y3,currentZ+stepSize),mf.getColor()));
                            } else {
                                cam.render3DTriangle(new Triangle(new Vec3(currentX,y1,currentZ),new Vec3(currentX+stepSize,y2,currentZ),new Vec3(currentX+stepSize,y3,currentZ+stepSize),mf.getColor()));
                                cam.render3DTriangle(new Triangle(new Vec3(currentX,y1,currentZ),new Vec3(currentX,y4,currentZ+stepSize),new Vec3(currentX+stepSize,y3,currentZ+stepSize),mf.getColor()));
                            }
                        }

                    }
                }
                currentZ = (float) (cam.getPos().z - zInLoop);
            }

            //Z
            //--------------------------------------------------------------------------------------------------
            currentX = (float) (cam.getPos().x - xInLoop);
            currentY = (float) (cam.getPos().y - yInLoop);
            currentZ = (float) (cam.getPos().z - zInLoop);

            for (int i = 0; i < conf.getNumberOfTrianglesX(); i++) {
                currentX += stepSize;
                for (int j = 0; j < conf.getNumberOfTrianglesY(); j++) {
                    currentY += stepSize;

                    for (Mathfunction mf: mfs) {

                        if (mf.getCurrentAxis() == Mathfunction.Axis.z) {
                            double z1 = mf.exec3DFunction(currentX,currentY,0);
                            double z2 = mf.exec3DFunction(currentX + stepSize,currentY,0);
                            double z3 = mf.exec3DFunction(currentX + stepSize,currentY + stepSize,0);
                            double z4 = mf.exec3DFunction(currentX,currentY + stepSize,0);

                            if (conf.getGraphingTrianglesFilled()) {
                                cam.render3DFilledTriangle(new Triangle(new Vec3(currentX,currentY,z1),new Vec3(currentX+stepSize,currentY,z2),new Vec3(currentX+stepSize,currentY+stepSize,z3),mf.getColor()));
                                cam.render3DFilledTriangle(new Triangle(new Vec3(currentX,currentY,z1),new Vec3(currentX,currentY+stepSize,z4),new Vec3(currentX+stepSize,currentY+stepSize,z3),mf.getColor()));
                            } else {
                                cam.render3DTriangle(new Triangle(new Vec3(currentX,currentY,z1),new Vec3(currentX+stepSize,currentY,z2),new Vec3(currentX+stepSize,currentY+stepSize,z3),mf.getColor()));
                                cam.render3DTriangle(new Triangle(new Vec3(currentX,currentY,z1),new Vec3(currentX,currentY+stepSize,z4),new Vec3(currentX+stepSize,currentY+stepSize,z3),mf.getColor()));
                            }
                        }

                    }
                }
                currentY = (float) (cam.getPos().y - yInLoop);
            }
        }

        renderPanel.setScreen(cam.getScreen());
        renderPanel.repaint();

        cmdLine.setConfig(conf);
    }

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

    private void focusRequest() {
        this.requestFocus();
    }

    private JFrame getFrame() {
        return this;
    }
}
