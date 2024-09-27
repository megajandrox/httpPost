package commons.io.utils.strategy;

import commons.io.utils.exceptions.FileIOException;

import java.io.File;

public class FileDelete implements FileOperationStrategy {

    @Override
    public boolean execute(FileInputParameter fileParameter) throws FileIOException {
        File file = new File(fileParameter.getSourceFile());
        boolean deleted = file.delete();
        if (!deleted) {
            throw new FileIOException(String.format("Failed to delete file: %s", file.getAbsolutePath()));
        }
        return true;
    }
}
