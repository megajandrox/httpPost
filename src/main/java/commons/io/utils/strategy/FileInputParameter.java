package commons.io.utils.strategy;

import java.io.File;

public class FileInputParameter {

    private String sourceFile;
    private String destinationFile;

    public FileInputParameter(String file) {
        this.sourceFile = file;
    }

    public FileInputParameter(String sourceFile, String destinationFile) {
        this(sourceFile);
        this.destinationFile = destinationFile;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public String getDestinationFile() {
        return destinationFile;
    }

    public File getSourceAsFile() {
        return new File(sourceFile);
    }

    public File getDestinationAsFile() {
        return new File(destinationFile);
    }
}
