package group17.Graphics.Assist;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;

/**
 * The type Controllers window.
 */
public class HelpSupportWindow extends JDesktopPane {

    /**
     * The Color of the background.
     */
    Color color = new Color(77, 142, 255);

    private int imageSelected = 1;
    private JPanel panel;
    private JInternalFrame intFrame;

    /**
     * Instantiates a new Controllers window.
     */
    public HelpSupportWindow() {
        this.setBorder(BorderFactory.createLineBorder(color, 10));
        this.init();
    }

    /**
     * Init.
     */
    public void init() {

        intFrame = new JInternalFrame("TUTORIAL", true, true, true, true);
        intFrame.setBounds(50, 0, 1000, 500);
        intFrame.setLocation(20, 20);
        intFrame.setVisible(true);
        intFrame.setFrameIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/help.png")).getFile()));
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
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));
        setUpButtons(southPanel);

        panel.add(southPanel, SOUTH);
        panel.add(new ImageViewer(loadImage(1)), 0);

        return panel;
    }

    private void setUpButtons(JPanel southPanel) {
        JButton left;
        southPanel.add(left = new JButton("<< first"));
        left.addActionListener(e -> {
            ImageViewer i;
            panel.remove(0);
            panel.add(i = new ImageViewer(loadImage(1)), 0);
            imageSelected = 1;
            i.repaint();
            intFrame.paintComponents(intFrame.getGraphics());
        });
        JButton prev;
        southPanel.add(prev = new JButton("< prev"));
        prev.addActionListener(e -> {
            ImageViewer i;
            panel.remove(0);
            imageSelected = (imageSelected == 1 ? 8 : imageSelected - 1);
            panel.add(i = new ImageViewer(loadImage(imageSelected)), 0);
            i.repaint();
            intFrame.paintComponents(intFrame.getGraphics());

        });
        JButton next;
        southPanel.add(next = new JButton("next >"));
        next.addActionListener(e -> {
            ImageViewer i;
            panel.remove(0);
            imageSelected = (imageSelected == 8 ? 1 : imageSelected + 1);
            panel.add(i = new ImageViewer(loadImage(imageSelected)), 0);
            i.repaint();
            intFrame.paintComponents(intFrame.getGraphics());
        });
        JButton right;
        southPanel.add(right = new JButton("last >>"));
        right.addActionListener(e -> {
            ImageViewer i;
            panel.remove(0);
            panel.add(i = new ImageViewer(loadImage(8)), 0);
            imageSelected = 8;
            i.repaint();
            intFrame.paintComponents(intFrame.getGraphics());
        });
    }


    private Image loadImage(int i) {
        return new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/" + i + ".jpg")).getFile()).getImage();
    }

    /**
     * The type Image viewer.
     */
    public class ImageViewer extends JPanel {
        private final java.awt.Image image;
        private final boolean stretched = true;
        private int xCoordinate;
        private int yCoordinate;

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
         * Is stretched boolean.
         *
         * @return the boolean
         */
        public boolean isStretched() {
            return stretched;
        }
    }

}
