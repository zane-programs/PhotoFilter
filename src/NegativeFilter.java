/**
 * Represents a negative filter, akin to a film negative.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NegativeFilter implements Filter {
    @Override
    public String getName() {
        return "Negative";
    }

    @Override
    public String getFileExtension(File outputFile) {
        return FilterUtil.extractFileExtension(outputFile);
    }

    @Override
    public void transformImage(File srcFile, File outputFile) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(srcFile);

            // apply filter
            Color color;
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    color = new Color(image.getRGB(x, y));
                    color = new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue());
                    image.setRGB(x, y, color.getRGB());
                }
            }

            // write output
            ImageIO.write(image, this.getFileExtension(outputFile), outputFile);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
