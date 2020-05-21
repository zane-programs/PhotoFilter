/**
 * Represents a filter that flips an image across the x-axis.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ReverseFilter implements Filter{

    @Override
    public String getName() {
        return "Reverse";
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

            // reverse pixels in array
            Color[][] pixels = new Color[image.getWidth()][image.getHeight()];
            for (int x = image.getWidth() - 1; x >= 0; x--) {
                // outer loop (x) is in reverse to reverse order of pixels
                for (int y = 0; y < image.getHeight(); y++) {
                    pixels[x][y] = new Color(image.getRGB(image.getWidth() - x - 1, y));
                }
            }

            // now, apply the filter
            for (int x = 0; x < pixels.length; x++) {
                for (int y = 0; y < pixels[x].length; y++) {
                    image.setRGB(x, y, pixels[x][y].getRGB());
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