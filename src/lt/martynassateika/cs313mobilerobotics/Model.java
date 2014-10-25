package lt.martynassateika.cs313mobilerobotics;

import lt.martynassateika.cs313mobilerobotics.filters.ImageFilter2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Martynas Sateika
 * Date: 14.2.8
 * Time: 03.33
 */
public class Model {

    private final BufferedImage sourceImage;
    private final List<ImageFilter2D> filters;
    private BufferedImage filteredImage;

    public Model() {
        try {
            this.sourceImage = ImageIO.read(new File("assets/Lenna.png"));
        } catch (IOException e) {
            throw new RuntimeException("Error loading image");
        }

        this.filteredImage = sourceImage;

        // Set up filters
        this.filters = new ArrayList<>();
        this.filters.add(new ImageFilter2D("Sharpen", new double[][] {
                {-1.0d, -1.0d, -1.0d},
                {-1.0d, 9.0d, -1.0d},
                {-1.0d, -1.0d, -1.0d}
        }, 1.0d, 0.0d));
        this.filters.add(new ImageFilter2D("Blur", new double[][] {
                {0.0d, 0.2d, 0.0d},
                {0.2d, 0.2d, 0.2d},
                {0.0d, 0.2d, 0.0d},
        }, 1.0d, 0.0d));
        this.filters.add(new ImageFilter2D("Motion blur", new double[][] {
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1},
        }, 1.0d / 9.0d, 0.0d));
        this.filters.add(new ImageFilter2D("Detect edges", new double[][]{
                {0, 0, -1, 0, 0},
                {0, 0, -1, 0, 0},
                {0, 0, 4, 0, 0},
                {0, 0, -1, 0, 0},
                {0, 0, -1, 0, 0},
        }, 1.0d, 0.0d));
        this.filters.add(new ImageFilter2D("Horizontal edges", new double[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {-1, -1, 2, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
        }, 1.0d, 0.0d));
        this.filters.add(new ImageFilter2D("Vertical edges", new double[][]{
                {0, 0, -1, 0, 0},
                {0, 0, -1, 0, 0},
                {0, 0, 4, 0, 0},
                {0, 0, -1, 0, 0},
                {0, 0, -1, 0, 0},
        }, 1.0d, 0.0d));
    }

    public BufferedImage getSourceImage() {
        return sourceImage;
    }

    public List<ImageFilter2D> getFilters() {
        return filters;
    }

    public void setFilteredImage(BufferedImage filteredImage) {
        this.filteredImage = filteredImage;
    }

    public BufferedImage getFilteredImage() {
        return filteredImage;
    }
}
