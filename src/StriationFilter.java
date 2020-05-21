/**
 * Represents a grayscale filter that converts color images to grayscale images.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StriationFilter implements Filter{
    private final int DIFFERENCE_MAGNITUDE = 40;

    @Override
    public String getName() {
        return "Striated";
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
            int avg, difference;
            Color color, finalColor;
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    color = new Color(image.getRGB(x, y));
                    avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    difference = (int) getAdjustmentFromAverage(avg); // gets whether to increase or decrease brightness
                    // make new color with that difference
                    finalColor = new Color(FilterUtil.capColor(color.getRed() + difference), FilterUtil.capColor(color.getGreen() + difference), FilterUtil.capColor(color.getBlue() + difference));
                    image.setRGB(x, y, finalColor.getRGB());
                }
            }

            // write output
            ImageIO.write(image, this.getFileExtension(), outputFile);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /* helper methods: */

    /**
     * Gets the number to add or subtract to/from
     *
     * @param avg (0-255)
     * @return adjusted value
     */
    private float getAdjustmentFromAverage(int avg) {
//        float sign = (avg > 127) ? 1 : -1; // 127 is midpoint
        float sign = 1;
        // woahhh this is a bear!
        float changer = (float) (0.5 * (Math.cos((((double) avg / 255) * 360) + 0.5) * (Math.PI / 180)));
//        System.out.println(changer + ", " + changer * DIFFERENCE_MAGNITUDE * DIFFERENCE_MAGNITUDE * sign);
        return changer * DIFFERENCE_MAGNITUDE * DIFFERENCE_MAGNITUDE * sign;
    }

}