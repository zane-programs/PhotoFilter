/**
 * Represents a negative filter, akin to a film negative.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpicyFilter implements Filter {
    private final int GRAIN_SIDE_SIZE = 6; // how big each averaged area should be

    @Override
    public String getName() {
        return "Spicy";
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
            Color finalColor;
            Color[][] colors;
            // nested loop for matrix manipulation
            for (int x = 0; x < image.getWidth(); x += 2) {
                for (int y = 0; y < image.getHeight(); y += 2) {
                    colors = new Color[GRAIN_SIDE_SIZE][GRAIN_SIDE_SIZE]; // color storage for avg and replace
                    // fill the array with colors
                    for (int i = 0; i < GRAIN_SIDE_SIZE; i++) {
                        for (int j = 0; j < GRAIN_SIDE_SIZE; j++) {
                            // it's a square.
                            if (x + i + 1 <= image.getWidth() && y + j + 1 <= image.getHeight()) {
                                colors[i][j] = new Color(image.getRGB(x + i, y + j));
                                if (colors[i][j] == null) colors[i][j] = new Color(0, 0, 0);
                            }
                        }
                    }

                    // averages colors within the selected grain square
                    finalColor = averageColors(flatten2DArray(colors));

                    // set that color for all of the pixels that were pulled from for average
                    for (int i = 0; i < colors.length; i++) {
                        for (int j = 0; j < colors[i].length; j++) {
                            if (x + i + 1 <= image.getWidth() && y + j + 1 <= image.getHeight())
                                image.setRGB(x + i, y + j, finalColor.getRGB());
                        }
                    }
                }
            }

            // write output
            ImageIO.write(image, this.getFileExtension(outputFile), outputFile);

        } catch (IOException e) {
            // we had an oops!
            e.printStackTrace();
            return;
        }
    }

    /* helper methods */
    private int randIntBetween(int lowerBound, int upperBound) {
        return (int) (Math.random() * upperBound) + lowerBound;
    }

    private int randSignRandIntBetween(int lowerBound, int upperBound) {
        int sign = (Math.random() >= 0.5) ? 1 : -1;
        return sign * randIntBetween(lowerBound, upperBound);
    }

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

        //
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
