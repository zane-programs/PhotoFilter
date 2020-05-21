import java.io.File;

public interface Filter {
    String getName();
    String getFileExtension();
    void transformImage(File srcFile, File outputFile);
}
