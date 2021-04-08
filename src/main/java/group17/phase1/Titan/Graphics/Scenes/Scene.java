package group17.phase1.Titan.Graphics.Scenes;
import group17.phase1.Titan.Graphics.Geometry.Point3D;
import group17.phase1.Titan.Graphics.Geometry.Point3DConverter;
import group17.phase1.Titan.Graphics.GraphicsManager;
import group17.phase1.Titan.Graphics.MouseInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Scene extends JPanel
{
    public MouseInput mouse;

    private int initialX, initialY;
    protected final static double mouseSensitivity = 16;
    static double deltaX, deltaY = 0;
    static double totalxDif, totalyDif = 0;
    static double scale = 3e8;
    double radiusMag = 1e2;
    private final static int UNIT_SIZE = GraphicsManager.screen.width ;//*(int) scale;
    private BufferedImage image;


    protected Point3D left = new Point3D(-UNIT_SIZE,0,0),
            right = new Point3D(UNIT_SIZE,0,0),
            top = new Point3D(0,UNIT_SIZE,0),
            bottom = new Point3D(0,-UNIT_SIZE,0),
            front = new Point3D(0,0,UNIT_SIZE),
            rear = new Point3D(0,0,-UNIT_SIZE);



    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        if (image == null) {
            create();
            setHints(g);
        }
        else {
            g.drawImage(image,0,0,GraphicsManager.screen.width,GraphicsManager.screen.height,0,0,image.getWidth(),image.getHeight(),new Color(0,0,0, 111),null);
        }


        //g.setColor(Color.BLACK);
        //g.fillRect(0,0,GraphicsManager.screen.width,GraphicsManager.screen.height);
    }

    public void setHints(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_SPEED);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }

    void create(){
        try {
            image = ImageIO.read(new File("src/main/java/group17/phase1/Titan/milkyway.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void init();

    public void update()
    {
        int x = this.mouse.getX();
        int y = this.mouse.getY();

        deltaX = 0;
        deltaY = 0;

        if(this.mouse.getButton() == MouseInput.ClickType.LeftClick) {
            int xDif = x - initialX;
            deltaX = xDif;
            this.rotateOnAxisY(false,xDif/mouseSensitivity);
            totalxDif += xDif;
        }

        else if(this.mouse.getButton() == MouseInput.ClickType.RightClick) {
            int yDif = y - initialY;
            deltaY = yDif;
            this.rotateOnAxisX(false,yDif/mouseSensitivity);
            totalyDif += yDif;
        }


        if(this.mouse.isScrollingUp()) {
            Point3DConverter.zoomIn();
        }
        else if(this.mouse.isScrollingDown()) {
            Point3DConverter.zoomOut();
        }

        this.mouse.resetScroll();

        this.initialX = x;
        this.initialY = y;
    }

    public void addMouseControl(MouseInput mouse){
        this.mouse = mouse;
    }

    public void rotateOnAxisY(boolean cw, double y){
        Point3DConverter.rotateAxisY(top,cw,y);
        Point3DConverter.rotateAxisY(bottom,cw,y);
        Point3DConverter.rotateAxisY(left,cw,y);
        Point3DConverter.rotateAxisY(right,cw,y);
        Point3DConverter.rotateAxisY(rear,cw,y);
        Point3DConverter.rotateAxisY(front,cw,y);
    }
    void rotateOnAxisX(boolean cw, double x)
    {
        Point3DConverter.rotateAxisX(top,cw,x);
        Point3DConverter.rotateAxisX(bottom,cw,x);
        Point3DConverter.rotateAxisX(left,cw,x);
        Point3DConverter.rotateAxisX(right,cw,x);
        Point3DConverter.rotateAxisX(rear,cw,x);
        Point3DConverter.rotateAxisX(front,cw,x);
    }

    void rotateOnAxisZ(boolean cw, double z){
        Point3DConverter.rotateAxisZ(top,cw,z);
        Point3DConverter.rotateAxisZ(bottom,cw,z);
        Point3DConverter.rotateAxisZ(left,cw,z);
        Point3DConverter.rotateAxisZ(right,cw,z);
        Point3DConverter.rotateAxisZ(rear,cw,z);
        Point3DConverter.rotateAxisZ(front,cw,z);
    }

    public enum SceneType{
        STARTING_SCENE,
        SIMULATION_SCENE,
        TRAJECTORIES
    }

}
