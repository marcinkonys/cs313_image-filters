package lt.martynassateika.cs313mobilerobotics.filters;

import lt.martynassateika.cs313mobilerobotics.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * User: Martynas Sateika
 * Date: 14.1.17
 * Time: 16.26
 */
public class ImageFilter2D extends ImageFilter {

    private final int filterSize;
    private final double factor;
    private final double bias;
    private final String name;

    /**
     * Constructor method, sets up the values for the image filter
     *
     * @param name Name of the filter
     * @param filterValues 2D array holding the filter's values
     * @param factor Factor by which the RG&B of the pixel is multiplied
     * @param bias Bias that is added to the RGB after multiplying by factor
     * @throws IllegalArgumentException If filterValues array invalid
     */
    public ImageFilter2D(String name, double[][] filterValues, double factor, double bias) throws IllegalArgumentException {
        super(filterValues);
        Objects.requireNonNull(name);
        Objects.requireNonNull(filterValues);

        if (filterValues[0].length != filterValues.length) {
            throw new IllegalArgumentException("Supplied matrix not square!");
        }

        if (filterValues[0].length % 2 == 0) {
            throw new IllegalArgumentException("Matrix needs odd width/height!");
        }

        this.name = name;
        this.factor = factor;
        this.bias = bias;
        this.filterSize = filterValues.length;
    }

    /**
     * Applies itself to supplied image and returns
     * its copy (by invoking ImageUtils.copyImage()
     *
     * @param image Image to apply filter to
     * @return New image with filter applied
     */
    @Override
    public BufferedImage applyFilter(BufferedImage image) {
        BufferedImage newImage = ImageUtils.copyImage(image);

        for (int imageX = 0; imageX < image.getWidth(); imageX++) {
            for (int imageY = 0; imageY < image.getHeight(); imageY++) {

                // Reset RGB after every pass through the filter
                double red = 0.0d;
                double green = 0.0d;
                double blue = 0.0d;

                // Process the filter
                for (int filterX = 0; filterX < this.filterValues.length; filterX++) {
                    for (int filterY = 0; filterY < this.filterValues.length; filterY++) {

                        // Calculate the coordinate of the image on which
                        // the filter is, given the image coordinate that the
                        // filter started on, and the current filterX and
                        // filterY which we're looping through. If we're on the
                        // extreme ends of the image, the filter might wrap
                        // around the image, hence the % (modulo) sign
                        int _imageX = (imageX - (this.filterSize / 2) + filterX + image.getWidth()) % image.getWidth();
                        int _imageY = (imageY - (this.filterSize / 2) + filterY + image.getHeight()) % image.getHeight();

                        // For every filter's cell, we multiply the image's
                        // RGB values at the current position with the cell's
                        // value, and accumulate these products
                        Color pixelValues = new Color(image.getRGB(_imageX, _imageY));
                        red += pixelValues.getRed() * filterValues[filterX][filterY];
                        green += pixelValues.getGreen() * filterValues[filterX][filterY];
                        blue += pixelValues.getBlue() * filterValues[filterX][filterY];
                    }
                }

                // After going through all the cells in the filter at
                // some coordinate in the image, we update the image's RGB
                // values at that point with the new, accumulated RGB data
                newImage.setRGB(imageX, imageY, new Color(
                    Math.max(Math.min((int) (factor * red + bias), 255), 0),
                    Math.max(Math.min((int)(factor * green + bias), 255), 0),
                    Math.max(Math.min((int)(factor * blue + bias), 255), 0)
                ).getRGB());
            }
        }
        return newImage;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
