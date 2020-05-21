/**
 * Represents a grayscale filter that converts color images to grayscale images.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GhostFilter implements Filter {
    @Override
    public String getName() {
        return "Ghost";
    }

    @Override
    public String getFileExtension() {
        return "png";
    }

    @Override
    public void transformImage(File srcFile, File outputFile) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(srcFile);

            // apply filter
            int avg;
            Color color;
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    color = new Color(image.getRGB(x, y));
                    avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3 + 25;
                    if (avg > 255) avg = 255; // catch colors that would be > 255
                    color = new Color(avg, avg, avg, 80);
                    image.setRGB(x, y, color.getRGB());
                }
            }

            // write output
            ImageIO.write(image, this.getFileExtension(), outputFile);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}