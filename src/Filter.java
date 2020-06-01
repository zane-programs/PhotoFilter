import java.io.File;

public interface Filter {
    String getName();
    String getFileExtension(File outputFile);
    void transformImage(File srcFile, File outputFile);
}
