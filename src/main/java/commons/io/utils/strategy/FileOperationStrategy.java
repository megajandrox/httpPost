package commons.io.utils.strategy;

import commons.io.utils.exceptions.FileIOException;

public interface FileOperationStrategy {

    boolean execute(FileInputParameter fileParameter) throws FileIOException;
}
