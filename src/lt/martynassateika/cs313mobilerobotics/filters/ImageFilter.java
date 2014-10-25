package lt.martynassateika.cs313mobilerobotics.filters;

import java.awt.image.BufferedImage;

/**
 * User: Martynas Sateika
 * Date: 14.1.20
 * Time: 13.08
 */
public abstract class ImageFilter {

    protected final double[][] filterValues;

    public ImageFilter(double[][] filterValues) {
        this.filterValues = filterValues;
    }

    abstract BufferedImage applyFilter(BufferedImage image);
}
