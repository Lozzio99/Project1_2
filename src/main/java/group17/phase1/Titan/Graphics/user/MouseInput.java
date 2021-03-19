
/**
 * This class handles the mouse input for the GUI.
 * @author 	Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan​
 * @version 1.0
 * @since	19/02/2021
 */
package group17.phase1.Titan.Graphics.user;

import java.awt.event.*;

public class MouseInput extends MouseMotionAdapter implements MouseListener, MouseWheelListener
{

    // Variables
    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseB = -1;
    private int scroll = 0;

    /**
     * Returns the X position of the mouse.
     * @return
     */
    public int getX()
    {
        return this.mouseX;
    }

    /**
     * Returns the Y position of the mouse.
     * @return
     */
    public int getY()
    {
        return this.mouseY;
    }

    /**
     * Returns the state, whether the user is currently scrolling up or not.
     * @return
     */
    public boolean isScrollingUp()
    {
        return this.scroll == -1;
    }

    /**
     * Returns the state, whether the user is currently scrolling down or not.
     * @return
     */
    public boolean isScrollingDown()
    {
        return this.scroll == 1;
    }

    /**
     * Resets the scroll of the mouse.
     */
    public void resetScroll()
    {
        this.scroll = 0;
    }

    /**
     * Returns the type of the button, which is pressed.
     * @return
     */
    public ClickType getButton()
    {
        switch(this.mouseB)
        {
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

    /**
     * Resets the mouse button.
     */
    public void resetButton()
    {
        this.mouseB = -1;
    }

    @Override
    /**
     * Handles movement of the mouse wheel.
     */
    public void mouseWheelMoved(MouseWheelEvent event)
    {
        scroll = event.getWheelRotation();
    }

    @Override
    /**
     * Handles dragged movement of the mouse.
     */
    public void mouseDragged(MouseEvent event)
    {
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }

    @Override
    /**
     * Handles the general movement of the mouse.
     */
    public void mouseMoved(MouseEvent event)
    {
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }

    @Override
    /**
     * Handles a mouse click.
     */
    public void mouseClicked(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
    }

    @Override
    /**
     * Handles the cursor entering the frame.
     */
    public void mouseEntered(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
    }

    @Override
    /**
     * Handles the cursor exiting the frame.
     */
    public void mouseExited(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
    }

    @Override
    /**
     * Handles a pressed mouse button.
     */
    public void mousePressed(MouseEvent event)
    {
        this.mouseB = event.getButton();
    }

    @Override
    /**
     * Handles a released mouse button.
     */
    public void mouseReleased(MouseEvent arg0)
    {
        this.mouseB = -1;
    }

    /**
     * Enumerates the click type of the mouse.
     */
    public enum ClickType
    {
        Unknown,
        LeftClick,
        ScrollClick,
        RightClick,
        ForwardPage,
        BackPage
    }
}
