package lt.martynassateika.cs313mobilerobotics;

import lt.martynassateika.cs313mobilerobotics.filters.ImageFilter2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * User: Martynas Sateika
 * Date: 14.2.8
 * Time: 03.33
 */
public class View {

    private final ImagePanel imagePanel;
    private final JFrame frame;
    private final JComboBox<ImageFilter2D> imageFilterComboBox;

    public View(String title) {
        this.frame = new JFrame(title);

        JPanel panel = new JPanel(new BorderLayout());
        this.imageFilterComboBox = new JComboBox<>();
        this.imagePanel = new ImagePanel();

        panel.add(this.imageFilterComboBox, BorderLayout.NORTH);
        panel.add(this.imagePanel, BorderLayout.CENTER);

        this.frame.add(panel);

        this.frame.pack();
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
    }

    public void setSize(int width, int height) {
        this.frame.setSize(width, height);
        this.imagePanel.setSize(width, height);
    }

    public void refresh(BufferedImage sourceImage, BufferedImage destinationImage) {
        this.imagePanel.refresh(sourceImage, destinationImage);
    }

    private class ImagePanel extends JPanel {

        private BufferedImage[] images = new BufferedImage[0];

        public void refresh(BufferedImage...images) {
            this.images = images;
            this.repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D)g;

            int xOffset = 0;
            for (BufferedImage image : images) {
                graphics2D.drawImage(image, xOffset, 0, image.getWidth(), image.getHeight(), null);
                xOffset += image.getWidth();
            }
        }
    }

    public JComboBox<ImageFilter2D> getImageFilterComboBox() {
        return imageFilterComboBox;
    }
}
