import java.awt.*;
import java.io.File;

public class FilterUtil {
    /**
     * Caps the color value given. In other words,
     * if it is given a value greater than 255, then
     * it will return 255. If the value is less than
     * 0, it will return 0. Otherwise, it'll return
     * the given parameter.
     *
     * @param colorVal (any int)
     * @return capped color value
     */
    public static int capColor(int colorVal) {
        if (colorVal < 0) {
            // color under lower bound (0)
            return 0;
        } else if (colorVal > 255) {
            // color over upper bound (255)
            return 255;
        } else {
            // color is fine as-is (0-255)
            return colorVal;
        }
    }

    /**
     * Returns the file extension of a given
     * file. This little snippet was written
     * from Austin Totty (grabbed from the
     * original PhotoFilter repo) and
     * re-purposed for my needs.
     *
     * @param file
     * @return extension of the file
     */
    public static String extractFileExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".")+1);
    }

    /**
     * Creates Color object with randomized RGB values
     *
     * @return Color object w/ random RGB
     */
    public static Color genRandColor() {
        return new Color(genRandRGBValue(), genRandRGBValue(), genRandRGBValue());
    }

    /**
     * Generates a random RGB value between 0 and 255
     *
     * @return RGB value (int) 0-255
     */
    private static int genRandRGBValue() {
        return (int) (Math.random() * 256);
    }
}
