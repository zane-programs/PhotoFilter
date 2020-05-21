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
}
