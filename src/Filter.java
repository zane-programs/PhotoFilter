import java.io.File;

public interface Filter {
    String getName();
    void transformImage(File srcFile, File outputFile);
}
