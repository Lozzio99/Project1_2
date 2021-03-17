package group17.phase1.Titan.Graphics.user;

import java.awt.event.*;

public class MouseInput extends MouseMotionAdapter implements MouseListener, MouseWheelListener
{

    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseB = -1;
    private int scroll = 0;

    public int getX() {
        return this.mouseX;
    }

    public int getY() {
        return this.mouseY;
    }

    public boolean isScrollingUp() {
        return this.scroll == -1;
    }

    public boolean isScrollingDown() {
        return this.scroll == 1;
    }

    public void resetScroll() {
        this.scroll = 0;
    }

    public ClickType getButton() {
        switch(this.mouseB) {
            case 1:
                return ClickType.LeftClick;
            case 2:
                return ClickType.ScrollClick;
            case 3:
                return ClickType.RightClick;
            case 4:
                return ClickType.BackPage;
            case 5:
                return ClickType.ForwardPage;
            default:
                return ClickType.Unknown;
        }
    }

    public void resetButton() {
        this.mouseB = -1;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        scroll = event.getWheelRotation();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent event) {
        this.mouseB = event.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        this.mouseB = -1;
    }


    public enum ClickType {
        Unknown,
        LeftClick,
        ScrollClick,
        RightClick,
        ForwardPage,
        BackPage
    }
}
