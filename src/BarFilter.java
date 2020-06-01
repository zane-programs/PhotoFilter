/**
 * Represents a mirror filter that mirrors images down the middle.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarFilter implements Filter{

    @Override
    public String getName() {
        return "Barred";
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
            for (int y = 0; y < image.getHeight(); y++) {
                int rgbVal = FilterUtil.genRandColor().getRGB();
                for (int x = 0; x < image.getWidth(); x++) {
                    if (y % 5 == 0) {
                        image.setRGB(x, y, rgbVal);
                    }
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