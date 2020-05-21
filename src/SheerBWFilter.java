/**
 * Represents a grayscale filter that converts color images to grayscale images.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SheerBWFilter implements Filter{

    @Override
    public String getName() {
        return "SheerBW";
    }

    @Override
    public String getFileExtension() {
        return "jpg";
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
                    avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    int newColor;
                    // if brighter than 127, white. if not, black
                    if (avg > 127) {
                        newColor = 255;
                    } else {
                        newColor = 0;
                    }
                    // set new color
                    color = new Color(newColor, newColor, newColor);
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