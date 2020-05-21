/**
 * Represents a negative filter, akin to a film negative.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SpicyFilter implements Filter {
    private final int GRAIN_SIDE_SIZE = 6;

    @Override
    public String getName() {
        return "Spicy";
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
//            Color color1, color2, color3, color4, finalColor;
            Color finalColor;
            Color[][] colors;
            for (int x = 0; x < image.getWidth(); x += 2) {
                for (int y = 0; y < image.getHeight(); y += 2) {
//                    color1 = new Color(image.getRGB(x, y));
//                    color2 = new Color(image.getRGB(x + 1, y));
//                    color3 = new Color(image.getRGB(x, y + 1));
//                    color4 = new Color(image.getRGB(x + 1, y + 1));
                    colors = new Color[GRAIN_SIDE_SIZE][GRAIN_SIDE_SIZE];
                    for (int i = 0; i < GRAIN_SIDE_SIZE; i++) {
                        for (int j = 0; j < GRAIN_SIDE_SIZE; j++) {
                            // it's a square.
                            if (x + i + 1 <= image.getWidth() && y + j + 1 <= image.getHeight()) {
                                colors[i][j] = new Color(image.getRGB(x + i, y + j));
                                if (colors[i][j] == null) colors[i][j] = new Color(0, 0, 0);
                            }
                        }
                    }

                    finalColor = averageColors(flatten2DArray(colors));

                    for (int i = 0; i < colors.length; i++) {
                        for (int j = 0; j < colors[i].length; j++) {
                            if (x + i + 1 <= image.getWidth() && y + j + 1 <= image.getHeight())
                                image.setRGB(x + i, y + j, finalColor.getRGB());
                        }
                    }
//                    image.setRGB(x, y, finalColor.getRGB());
//                    image.setRGB(x + 1, y, finalColor.getRGB());
//                    image.setRGB(x, y + 1, finalColor.getRGB());
//                    image.setRGB(x + 1, y + 1, finalColor.getRGB());
                }
            }

            // write output
            ImageIO.write(image, this.getFileExtension(), outputFile);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /* helper methods */
    private float averageFour(int num1, int num2, int num3, int num4) {
        return (((float) num1) + num2 + num3 + num4) / 4;
    }

    private int randIntBetween(int lowerBound, int upperBound) {
        return (int) (Math.random() * upperBound) + lowerBound;
    }

    private int randSignRandIntBetween(int lowerBound, int upperBound) {
        int sign = (Math.random() >= 0.5) ? 1 : -1;
        return sign * randIntBetween(lowerBound, upperBound);
    }

//    private Color averageColors(Color color1, Color color2, Color color3, Color color4) {
//        int red = FilterUtil.capColor((int) averageFour(color1.getRed(), color2.getRed(), color3.getRed(), color4.getRed()) + randSignRandIntBetween(10, 25));
//        int green = FilterUtil.capColor((int) averageFour(color1.getGreen(), color2.getGreen(), color3.getGreen(), color4.getGreen()) + randSignRandIntBetween(10, 25));
//        int blue = FilterUtil.capColor((int) averageFour(color1.getBlue(), color2.getBlue(), color3.getBlue(), color4.getBlue()) + randSignRandIntBetween(10, 25));
//
////        System.out.println("rgb(" + red + ", " + green + ", " + blue + ")");
//
//        return new Color(red, green, blue);
//    }

    private int randomifyColorValue(int val) {
        return FilterUtil.capColor(val + randSignRandIntBetween(5, 20));
    }

    private Color averageColors(ArrayList<Color> colorList) {
//        System.out.println(colorList);
        int redTally = 0, greenTally = 0, blueTally = 0;
        for (Color color : colorList) {
            redTally += color.getRed();
            greenTally += color.getGreen();
            blueTally += color.getBlue();
        }

        // actual averaging
        redTally /= colorList.size();
        greenTally /= colorList.size();
        blueTally /= colorList.size();

        // shake it up!
        redTally = randomifyColorValue(redTally);
        greenTally = randomifyColorValue(greenTally);
        blueTally = randomifyColorValue(blueTally);

        // really give it that SPICE
        redTally = FilterUtil.capColor(redTally + randIntBetween(10, 15));

        return new Color(redTally, greenTally, blueTally);
    }

    private ArrayList<Color> flatten2DArray(Color[][] colors) {
        ArrayList<Color> listToReturn = new ArrayList<>();
        for (Color[] colorList : colors) {
            for (Color color : colorList) {
                if (color != null) {
                    listToReturn.add(color);
                }
            }
        }
        return listToReturn;
    }
}
