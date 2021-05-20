package group17.Graphics.Assist;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;

/**
 * The type Controllers window.
 */
public class ControllersWindow extends JDesktopPane {

    //TODO : Maybe this will be HELP / INFO / DEMO

    private final String text = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    /**
     * The Color of the background.
     */
    Color color = new Color(77, 142, 255);


    /**
     * Instantiates a new Controllers window.
     */
    public ControllersWindow() {
        this.setBorder(BorderFactory.createLineBorder(color, 10));

        this.init();
    }

    /**
     * Init.
     */
    public void init() {

        JInternalFrame intFrame = new JInternalFrame("TUTORIAL", true, true, true, true);
        intFrame.setBounds(50, 90, 600, 450);
        intFrame.setLocation(20, 20);
        intFrame.setVisible(true);
        intFrame.setFrameIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/death.png")).getFile()));
        /*
        JTextArea textArea = new JTextArea(text,10,30);
        textArea.setEditable(true);
        JScrollPane pane = new JScrollPane(textArea);
        intFrame.add(pane);
         */

        intFrame.add(this.CreateImage());

        this.add(intFrame, WEST);
    }

    /**
     * Create image j panel.
     *
     * @return the j panel
     */
    public JPanel CreateImage() {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.add(new JButton("<<"));
        southPanel.add(new JButton("<"));
        southPanel.add(new JButton(">"));
        southPanel.add(new JButton(">>"));
        panel.add(southPanel, SOUTH);
        Image image1 = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/death.png")).getFile()).getImage();

        /*
        Image image2 = new ImageIcon("joe.jpg").getImage();
        Image image3 = new ImageIcon("sidney.jpg").getImage();
        Image image4 = new ImageIcon("bugs.gif").getImage();
        Image image5 = new ImageIcon("mac.jpg").getImage();
        Image image6 = new ImageIcon("snooki.jpg").getImage();
         */

        panel.add(new ImageViewer(image1));

        /*add(new ImageViewer(image2));// <== extra lines form first viewer attempt
         add(new ImageViewer(image3)); //, <== which showed all images at once.
         add(new ImageViewer(image4));// <== only need one image and to flip
         add(new ImageViewer(image5));// <== to a random image
         add(new ImageViewer(image6));// <==     */

        return panel;
    }

    /**
     * The type Image viewer.
     */
    public class ImageViewer extends JPanel {
        private java.awt.Image image;
        private boolean stretched = true;
        private int xCoordinate;
        private int yCoordinate;

        /**
         * Instantiates a new Image viewer.
         */
        public ImageViewer() {
        }

        /**
         * Instantiates a new Image viewer.
         *
         * @param image the image
         */
        public ImageViewer(Image image) {
            this.image = image;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null)
                if (isStretched())
                    g.drawImage(image, xCoordinate, yCoordinate, getWidth(), getHeight(), this);
                else
                    g.drawImage(image, xCoordinate, yCoordinate, this);
        }

        /**
         * Gets image.
         *
         * @return the image
         */
        public java.awt.Image getImage() {
            return image;
        }

        /**
         * Sets image.
         *
         * @param image the image
         */
        public void setImage(java.awt.Image image) {
            this.image = image;
            repaint();
        }

        /**
         * Is stretched boolean.
         *
         * @return the boolean
         */
        public boolean isStretched() {
            return stretched;
        }

        /**
         * Sets stretched.
         *
         * @param stretched the stretched
         */
        public void setStretched(boolean stretched) {
            this.stretched = stretched;
            repaint();
        }

        /**
         * Gets x coordinate.
         *
         * @return the x coordinate
         */
        public int getXCoordinate() {
            return xCoordinate;
        }

        /**
         * Sets x coodinate.
         *
         * @param xCoordinate the x coordinate
         */
        public void setXCoodinate(int xCoordinate) {
            this.xCoordinate = xCoordinate;
        }

        /**
         * Gets y coordinate.
         *
         * @return the y coordinate
         */
        public int getYCoordinate() {
            return yCoordinate;
        }

        /**
         * Sets y coodinate.
         *
         * @param yCoordinate the y coordinate
         */
        public void setYCoodinate(int yCoordinate) {
            this.yCoordinate = yCoordinate;
            repaint();
        }
    }

}
