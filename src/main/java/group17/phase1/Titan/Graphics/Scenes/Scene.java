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
import java.util.Objects;

public abstract class Scene extends JPanel
{
    public MouseInput mouse;

    protected static int initialX, initialY,x,y,xDif,yDif,deltaX,deltaY,totalXDif,totalYDif;
    protected final static double mouseSensitivity = 16;
    static double scale = 3e8;
    double radiusMag = 1e2;
    private final static int UNIT_SIZE = GraphicsManager.screen.width ;//*(int) scale;
    private BufferedImage image;
    private Graphics2D g;


    protected Point3D left = new Point3D(-UNIT_SIZE,0,0),
            right = new Point3D(UNIT_SIZE,0,0),
            top = new Point3D(0,UNIT_SIZE,0),
            bottom = new Point3D(0,-UNIT_SIZE,0),
            front = new Point3D(0,0,UNIT_SIZE),
            rear = new Point3D(0,0,-UNIT_SIZE);



    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        g = (Graphics2D) graphics;
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
        this.doLayout();
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    void create()
    {
        try {
            File f = new File(Objects.requireNonNull(Scene.class.getClassLoader().getResource("milky-way4k.jpg")).getFile());
            image = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void init();

    public void update()
    {
        x = this.mouse.getX();
        y = this.mouse.getY();
        deltaX = deltaY = 0;

        if(this.mouse.getButton() == MouseInput.ClickType.LeftClick) {
            deltaX = xDif = x - initialX;
            this.rotateOnAxisY(false,xDif/mouseSensitivity);
            totalXDif += xDif;
        }

        else if(this.mouse.getButton() == MouseInput.ClickType.RightClick) {
            deltaY = yDif = y - initialY;
            this.rotateOnAxisX(false,yDif/mouseSensitivity);
            totalYDif += yDif;
        }


        if(this.mouse.isScrollingUp()) {
            Point3DConverter.zoomIn();
        }
        else if(this.mouse.isScrollingDown()) {
            Point3DConverter.zoomOut();
        }

        //call this from the subclass
        //this.resetMouse();

    }

    public void addMouseControl(MouseInput mouse){
        this.mouse = mouse;
    }

    public void resetMouse(){
        this.mouse.resetScroll();
        initialX = x;
        initialY = y;
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
