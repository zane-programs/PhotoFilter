/**
 * Represents a PhotoFilter View and Controller. Creates a window with buttons and dropdowns
 * to select a source image, select a filter to apply, and to apply that filter.
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class PhotoFilter extends JFrame {
    JButton sourceButton, applyFilterButton;
    JFileChooser fileChooser;
    File srcFile, outputFile;
    FileNameExtensionFilter fileExtensionFilter;
    Filter filter;
    JTextArea log;

    public PhotoFilter() throws HeadlessException {
        // create window
        super("PhotoFilter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        setSize(500, 120);

        // filter selection
        /*
         * TODO: STUDENT WORK
         *  Add your filter names to the array filtersList to see them in the dropdown menu.
         */
        String[] filtersList = {
                            "Choose a Filter...",
                            "Grayscale",
                            "Negative",
                            "Ghost",
                            "Sheer B & W",
                            "Reverse",
                            "Mirror",
                            "Striated",
                            "Spicy"
                            };

        JComboBox filterDropdown = new JComboBox(filtersList);

        filterDropdown.addActionListener(e -> {
            String selectedFilter = (String)filterDropdown.getSelectedItem();
            if (selectedFilter.equals(filtersList[1])) {
                filter = new GrayscaleFilter();
            }
            else if (selectedFilter.equals(filtersList[2])) {
                filter = new NegativeFilter();
            }
            /*
             * TODO: STUDENT WORK
             *  Assign filter to the appropriate Filter object based on the dropdown selection.
             */
            else if (selectedFilter.equals(filtersList[3])) {
                filter = new GhostFilter();
            }
            else if (selectedFilter.equals(filtersList[4])) {
                filter = new SheerBWFilter();
            }
            else if (selectedFilter.equals(filtersList[5])) {
                filter = new ReverseFilter();
            }
            else if (selectedFilter.equals(filtersList[6])) {
                filter = new MirrorFilter();
            }
            else if (selectedFilter.equals(filtersList[7])) {
                filter = new StriationFilter();
            }
            else if (selectedFilter.equals(filtersList[8])) {
                filter = new SpicyFilter();
            }




            // no filter selected
            else {
                filter = null;
            }
        });

        // source selection
        fileChooser = new JFileChooser();
        fileExtensionFilter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg",
                                                            "PNG file", "png",
                                                            "GIF file", "gif");
        fileChooser.addChoosableFileFilter(fileExtensionFilter);
        fileChooser.setDialogTitle("Open");

        sourceButton = new JButton("Choose a Source...");
        sourceButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                srcFile = fileChooser.getSelectedFile();
                log.append(String.format("Selected source file: %s\n", srcFile.getName()));
            } else {
                log.append("Failed to open source file\n");
            }
        });

        // apply filter
        applyFilterButton = new JButton("Apply Filter");
        applyFilterButton.addActionListener(e -> {
            applyFilter();
            log.append(String.format("Filter %s applied. You can find the result in the file \n  %s\n",
                            filter.getName(), outputFile.getAbsolutePath()));
        });

        // log text bar
        log = new JTextArea(3,1);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        // add components to window
        JPanel mainPanel = new JPanel();
        mainPanel.add(sourceButton);
        mainPanel.add(filterDropdown);
        mainPanel.add(applyFilterButton);
        this.getContentPane().add(BorderLayout.NORTH, mainPanel);
        this.getContentPane().add(BorderLayout.SOUTH, logScrollPane);
    }

    /**
     * Applies the current filter to the current src image. If no src or if no filter are selected,
     * the method prints a helpful message to log, then returns.
     */
    private void applyFilter() {
        // check for valid params
        if (srcFile == null) {
            log.append("Please select a source image\n");
            return;
        }
        else if (filter == null) {
            log.append("Please select a filter\n");
            return;
        }

        // create output file
        String outputFileName = srcFile.getAbsolutePath().substring(0, srcFile.getAbsolutePath().indexOf(".")) +
                                "_" +
                                filter.getName() +
                                "." +
                                filter.getFileExtension();
        outputFile = new File(outputFileName);

        // transform image with filter
        filter.transformImage(srcFile, outputFile);
    }

    // main
    public static void main(String[] args) {
        PhotoFilter pf = new PhotoFilter();
        pf.setVisible(true);
    }
}
