package swingingAround;

import swingingAround.Cmd.Config;
import swingingAround.Cmd.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class GeoFrame extends JFrame {

    private ArrayList<Entity> entities;
    private PaintPanel renderPanel;
    //private JPanel panel;
    private JPanel configPanel;
    private Camera cam;
    //private JTextField cmdLine;
    private Console cmdLine;
    private Config conf;
    private boolean running = true;

    public GeoFrame() {
        this.setSize(1080,720);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setEnabled(true);
        this.setBounds(0,0,1080,720);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(new GridBagLayout());

        renderPanel = new PaintPanel(1080,720,108,72);
        renderPanel.setVisible(true);
        renderPanel.setEnabled(true);

        configPanel = new JPanel();

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
        conf.setGraphingTrianglesFilled(false);
        conf.setGraphingEnabled(true);
        conf.setNumberOfTrianglesX(50);
        conf.setNumberOfTrianglesY(50);
        conf.setNumberOfTrianglesZ(50);
        cmdLine.setConfig(conf);

        //conf.setGraphingTrianglesFilled(true);

        //Main loop?
        //--------------------------------------------------------------------------------------------------------------
        //!TODO
        //Kicerelni itt sok mindent draw functionre
        //Megjavitani a draw-t, hogy csak a teglatesten belul abrazoljon cuccokat.
        while (running) {
            try {
                //System.out.println("here");
                conf = cmdLine.getConfig();
                //System.out.println(conf.getEntities().size());
                //draw();

                ArrayList<Mathfunction> mfs = conf.getMathfunctions();
                renderPanel.setScreen(cam.getScreen());
                cam.screen.fillRectangle(0,0,cam.screen.getWidth(), cam.screen.getHeight(), new Color(30,30,30));

                if (conf.isGraphingEnabled()) {
                    //X
                    //--------------------------------------------------------------------------------------------------
                    for (int j = (int)  (cam.getPos().y - (conf.getNumberOfTrianglesY()/2)); j < (int) (cam.getPos().y + (conf.getNumberOfTrianglesY()/2)); j++) {
                        for (int k = (int) (cam.getPos().z - (conf.getNumberOfTrianglesZ()/2)); k < (int) (cam.getPos().z + (conf.getNumberOfTrianglesZ()/2)); k++) {
                            for (Mathfunction mf: mfs) {

                                if (mf.getCurrentAxis() == Mathfunction.Axis.x) {
                                    double x1 = mf.exec3DFunction(0, j, k);
                                    double x2 = mf.exec3DFunction(0, j + 1, k);
                                    double x3 = mf.exec3DFunction(0, j + 1, k + 1);
                                    double x4 = mf.exec3DFunction(0, j, k + 1);

                                    if (conf.getGraphingTrianglesFilled()) {
                                        cam.render3DFilledTriangle(new Triangle(new Vec3(x1,j,k),new Vec3(x2,j+1,k),new Vec3(x3,j+1,k+1),mf.getColor()));
                                        cam.render3DFilledTriangle(new Triangle(new Vec3(x1,j,k),new Vec3(x4,j,k+1),new Vec3(x3,j+1,k+1),mf.getColor()));
                                    } else {
                                        cam.render3DTriangle(new Triangle(new Vec3(x1,j,k),new Vec3(x2,j+1,k),new Vec3(x3,j+1,k+1),mf.getColor()));
                                        cam.render3DTriangle(new Triangle(new Vec3(x1,j,k),new Vec3(x4,j,k+1),new Vec3(x3,j+1,k+1),mf.getColor()));
                                    }
                                }

                            }
                        }
                    }

                    //Y
                    //--------------------------------------------------------------------------------------------------
                    for (int i = (int)  (cam.getPos().x - (conf.getNumberOfTrianglesX()/2)); i < (int) (cam.getPos().x + (conf.getNumberOfTrianglesX()/2)); i++) {
                        for (int k = (int) (cam.getPos().z - (conf.getNumberOfTrianglesZ()/2)); k < (int) (cam.getPos().z + (conf.getNumberOfTrianglesZ()/2)); k++) {
                            for (Mathfunction mf: mfs) {

                                if (mf.getCurrentAxis() == Mathfunction.Axis.y) {
                                    double y1 = mf.exec3DFunction(i,0,k);
                                    double y2 = mf.exec3DFunction(i + 1,0,k);
                                    double y3 = mf.exec3DFunction(i + 1,0,k + 1);
                                    double y4 = mf.exec3DFunction(i,0,k + 1);

                                    if (conf.getGraphingTrianglesFilled()) {
                                        cam.render3DFilledTriangle(new Triangle(new Vec3(i,y1,k),new Vec3(i+1,y2,k),new Vec3(i+1,y3,k+1),mf.getColor()));
                                        cam.render3DFilledTriangle(new Triangle(new Vec3(i,y1,k),new Vec3(i,y4,k+1),new Vec3(i+1,y3,k+1),mf.getColor()));
                                    } else {
                                        cam.render3DTriangle(new Triangle(new Vec3(i,y1,k),new Vec3(i+1,y2,k),new Vec3(i+1,y3,k+1),mf.getColor()));
                                        cam.render3DTriangle(new Triangle(new Vec3(i,y1,k),new Vec3(i,y4,k+1),new Vec3(i+1,y3,k+1),mf.getColor()));
                                    }
                                }

                            }
                        }
                    }

                    //Z
                    //--------------------------------------------------------------------------------------------------
                    for (int i = (int)  (cam.getPos().x - (conf.getNumberOfTrianglesX()/2)); i < (int) (cam.getPos().x + (conf.getNumberOfTrianglesX()/2)); i++) {
                        for (int j = (int) (cam.getPos().y - (conf.getNumberOfTrianglesY()/2)); j < (int) (cam.getPos().y + (conf.getNumberOfTrianglesY()/2)); j++) {
                            for (Mathfunction mf: mfs) {

                                if (mf.getCurrentAxis() == Mathfunction.Axis.z) {
                                    double z1 = mf.exec3DFunction(i,j,0);
                                    double z2 = mf.exec3DFunction(i + 1,j,0);
                                    double z3 = mf.exec3DFunction(i + 1,j + 1,0);
                                    double z4 = mf.exec3DFunction(i,j + 1,0);

                                    if (conf.getGraphingTrianglesFilled()) {
                                        cam.render3DFilledTriangle(new Triangle(new Vec3(i,j,z1),new Vec3(i+1,j,z2),new Vec3(i+1,j+1,z3),mf.getColor()));
                                        cam.render3DFilledTriangle(new Triangle(new Vec3(i,j,z1),new Vec3(i,j+1,z4),new Vec3(i+1,j+1,z3),mf.getColor()));
                                    } else {
                                        cam.render3DTriangle(new Triangle(new Vec3(i,j,z1),new Vec3(i+1,j,z2),new Vec3(i+1,j+1,z3),mf.getColor()));
                                        cam.render3DTriangle(new Triangle(new Vec3(i,j,z1),new Vec3(i,j+1,z4),new Vec3(i+1,j+1,z3),mf.getColor()));
                                    }
                                }

                            }
                        }
                    }
                }
                renderPanel.setScreen(cam.getScreen());
                renderPanel.repaint();

                cmdLine.setConfig(conf);

                sleep(50);
            } catch (Exception ex) {}
        }
    }

    private void draw() {
        ArrayList<Mathfunction> mfs = conf.getMathfunctions();
        renderPanel.setScreen(cam.getScreen());
        cam.screen.fillRectangle(0,0,cam.screen.getWidth(), cam.screen.getHeight(), new Color(30,30,30));

        ArrayList<Entity> entities = conf.getEntities();
        System.out.println(entities.size());
        System.out.println(conf.getEntities().size());
        for (Entity entity: entities) {
            System.out.println(entity.getName());
        }

        if (conf.isGraphingEnabled()) {
            //X
            //--------------------------------------------------------------------------------------------------
            for (int j = (int)  (cam.getPos().y - (conf.getNumberOfTrianglesY()/2)); j < (int) (cam.getPos().y + (conf.getNumberOfTrianglesY()/2)); j++) {
                for (int k = (int) (cam.getPos().z - (conf.getNumberOfTrianglesZ()/2)); k < (int) (cam.getPos().z + (conf.getNumberOfTrianglesZ()/2)); k++) {
                    for (Mathfunction mf: mfs) {

                        if (mf.getCurrentAxis() == Mathfunction.Axis.x) {
                            double x1 = mf.exec3DFunction(0, j, k);
                            double x2 = mf.exec3DFunction(0, j + 1, k);
                            double x3 = mf.exec3DFunction(0, j + 1, k + 1);
                            double x4 = mf.exec3DFunction(0, j, k + 1);

                            if (conf.getGraphingTrianglesFilled()) {
                                cam.render3DFilledTriangle(new Triangle(new Vec3(x1,j,k),new Vec3(x2,j+1,k),new Vec3(x3,j+1,k+1),mf.getColor()));
                                cam.render3DFilledTriangle(new Triangle(new Vec3(x1,j,k),new Vec3(x4,j,k+1),new Vec3(x3,j+1,k+1),mf.getColor()));
                            } else {
                                cam.render3DTriangle(new Triangle(new Vec3(x1,j,k),new Vec3(x2,j+1,k),new Vec3(x3,j+1,k+1),mf.getColor()));
                                cam.render3DTriangle(new Triangle(new Vec3(x1,j,k),new Vec3(x4,j,k+1),new Vec3(x3,j+1,k+1),mf.getColor()));
                            }
                        }

                    }
                }
            }

            //Y
            //--------------------------------------------------------------------------------------------------
            for (int i = (int)  (cam.getPos().x - (conf.getNumberOfTrianglesX()/2)); i < (int) (cam.getPos().x + (conf.getNumberOfTrianglesX()/2)); i++) {
                for (int k = (int) (cam.getPos().z - (conf.getNumberOfTrianglesZ()/2)); k < (int) (cam.getPos().z + (conf.getNumberOfTrianglesZ()/2)); k++) {
                    for (Mathfunction mf: mfs) {

                        if (mf.getCurrentAxis() == Mathfunction.Axis.y) {
                            double y1 = mf.exec3DFunction(i,0,k);
                            double y2 = mf.exec3DFunction(i + 1,0,k);
                            double y3 = mf.exec3DFunction(i + 1,0,k + 1);
                            double y4 = mf.exec3DFunction(i,0,k + 1);

                            if (conf.getGraphingTrianglesFilled()) {
                                cam.render3DFilledTriangle(new Triangle(new Vec3(i,y1,k),new Vec3(i+1,y2,k),new Vec3(i+1,y3,k+1),mf.getColor()));
                                cam.render3DFilledTriangle(new Triangle(new Vec3(i,y1,k),new Vec3(i,y4,k+1),new Vec3(i+1,y3,k+1),mf.getColor()));
                            } else {
                                cam.render3DTriangle(new Triangle(new Vec3(i,y1,k),new Vec3(i+1,y2,k),new Vec3(i+1,y3,k+1),mf.getColor()));
                                cam.render3DTriangle(new Triangle(new Vec3(i,y1,k),new Vec3(i,y4,k+1),new Vec3(i+1,y3,k+1),mf.getColor()));
                            }
                        }

                    }
                }
            }

            //Z
            //--------------------------------------------------------------------------------------------------
            for (int i = (int)  (cam.getPos().x - (conf.getNumberOfTrianglesX()/2)); i < (int) (cam.getPos().x + (conf.getNumberOfTrianglesX()/2)); i++) {
                for (int j = (int) (cam.getPos().y - (conf.getNumberOfTrianglesY()/2)); j < (int) (cam.getPos().y + (conf.getNumberOfTrianglesY()/2)); j++) {
                    for (Mathfunction mf: mfs) {

                        if (mf.getCurrentAxis() == Mathfunction.Axis.z) {
                            double z1 = mf.exec3DFunction(i,j,0);
                            double z2 = mf.exec3DFunction(i + 1,j,0);
                            double z3 = mf.exec3DFunction(i + 1,j + 1,0);
                            double z4 = mf.exec3DFunction(i,j + 1,0);

                            if (conf.getGraphingTrianglesFilled()) {
                                cam.render3DFilledTriangle(new Triangle(new Vec3(i,j,z1),new Vec3(i+1,j,z2),new Vec3(i+1,j+1,z3),mf.getColor()));
                                cam.render3DFilledTriangle(new Triangle(new Vec3(i,j,z1),new Vec3(i,j+1,z4),new Vec3(i+1,j+1,z3),mf.getColor()));
                            } else {
                                cam.render3DTriangle(new Triangle(new Vec3(i,j,z1),new Vec3(i+1,j,z2),new Vec3(i+1,j+1,z3),mf.getColor()));
                                cam.render3DTriangle(new Triangle(new Vec3(i,j,z1),new Vec3(i,j+1,z4),new Vec3(i+1,j+1,z3),mf.getColor()));
                            }
                        }

                    }
                }
            }
        }
        renderPanel.setScreen(cam.getScreen());
        renderPanel.repaint();
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

    private class cmdListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                System.out.println(cmdLine.getText());
                cmdLine.setText("");
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

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
                    //sSystem.exit(0);
                    break;

                case KeyEvent.VK_W:
                    if (cam == null)
                        System.out.println("Fuck nigga");
                    else
                        cam.setPos(new Vec3(cam.getPos().x,cam.getPos().y,cam.getPos().z+=1));
                    break;
                case KeyEvent.VK_S:
                    cam.setPos(new Vec3(cam.getPos().x,cam.getPos().y,cam.getPos().z-=1));
                    break;
                case KeyEvent.VK_A:
                    cam.setPos(new Vec3(cam.getPos().x-=1,cam.getPos().y,cam.getPos().z));
                    break;
                case KeyEvent.VK_D:
                    //cam.getPos();
                    //cam.setPos(cam.getPos());
                    cam.setPos(new Vec3(cam.getPos().x+=1,cam.getPos().y,cam.getPos().z));
                    break;

                default:
                    //renderPanel.drawTriangle(0,0,10,10,30,40,Color.GREEN);
                    renderPanel.repaint();
                    //defualt();
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