package lt.martynassateika.cs313mobilerobotics.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * User: Martynas Sateika
 * Date: 14.1.17
 * Time: 17.40
 */
public class ImageUtils {

    /**
     * Copies an image to a new object
     *
     * As the BufferedImage returned by BufferedImage.getSubImage() shares
     * its underlying pixel data with the old image, it is useless when
     * comparing images before and after the filtering. This method creates
     * an entirely new BufferedImage.
     *
     * @param image The image to copy
     * @return The new image
     */
    public static BufferedImage copyImage(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = newImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static BufferedImage convertToGrayScale(BufferedImage image) {
        BufferedImage newImage = ImageUtils.copyImage(image);
        int imageWidth = newImage.getWidth();
        int imageHeight = newImage.getHeight();

        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                Color color = new Color(newImage.getRGB(x, y));
                int colorAverage = (int)((color.getRed() + color.getGreen() + color.getBlue()) / 3.0d);
                newImage.setRGB(x, y, new Color(colorAverage, colorAverage, colorAverage).getRGB());
            }
        }

        return newImage;
    }

}
