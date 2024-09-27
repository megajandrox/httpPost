package commons.io.utils.strategy;

import commons.io.utils.exceptions.FileIOException;

public class FileProcessor {

    private FileOperationStrategy strategy;

    public FileProcessor(FileOperationStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean processFile(FileInputParameter file) throws FileIOException {
        return strategy.execute(file);
    }

    public void setStrategy(FileOperationStrategy strategy) {
        this.strategy = strategy;
    }

}
