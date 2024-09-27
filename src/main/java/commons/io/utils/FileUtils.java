package commons.io.utils;

import commons.io.utils.exceptions.FileIOException;
import commons.io.utils.strategy.*;

import java.io.File;

import static commons.io.utils.ValidationUtils.*;

/**
 *  Created by alejandro larosa. For more info visit https://github.com/alejandrolarosa/FileUtils
 *  This class is a wrapper for the FileProcessor class
 *  It provides a simple interface to copy, move, delete and read files
 *  It also provides a way to validate if the file exists, can read and can write
 *  It uses the FileProcessor class to execute the strategy
 *  It uses the FileCopy, FileDelete, FileRead and FileProcessor classes to execute the strategy
 *  It uses the FileInputParameter class to pass the parameters to the strategy
 *  It uses the ValidationUtils class to validate the file
 *  It uses the FileIOException class to throw exceptions
 *  It uses the FileUtils class to create a singleton instance
 */
public class FileUtils {

    private static FileUtils instance = new FileUtils();

    public static FileUtils getInstance() {
        return instance;
    }

    public boolean copy(String sourcePath,String destinationPath) throws FileIOException {
        FileInputParameter fp = new FileInputParameter(sourcePath, destinationPath);
        File sourceFile = fp.getSourceAsFile();
        File destinationFile = fp.getDestinationAsFile();
        sourceValidation(sourceFile);
        destinationValidation(destinationFile);
        FileProcessor processor = new FileProcessor(new FileCopy());
        return processor.processFile(fp);
    }

    public boolean move(String sourcePath,String destinationPath) throws FileIOException {
        FileInputParameter fp = new FileInputParameter(sourcePath, destinationPath);
        validateIfCanWrite(fp.getSourceAsFile());
        FileProcessor processor = new FileProcessor(new FileCopy());
        if(processor.processFile(fp)) {
            return this.delete(sourcePath);
        }
        return false;
    }

    public boolean delete(String file) throws FileIOException {
        FileInputParameter fp = new FileInputParameter(file);
        validateIfCanWrite(fp.getSourceAsFile());
        FileProcessor processor = new FileProcessor(new FileDelete());
        return processor.processFile(fp);
    }

    public String read(String file) throws FileIOException {
        return getReadFile(file).getFileContent();
    }

    public byte[] readBinary(String file) throws FileIOException {
        return getReadFile(file).getBinaryFileContent();
    }

    public boolean addContent(String file, String addContent) throws FileIOException {
        FileInputParameter fp = new FileInputParameter(file, file);
        validateIfCanWrite(fp.getSourceAsFile());
        String contentFile = this.read(file);
        FileProcessor processor = new FileProcessor(new FileWrite(contentFile.concat(addContent)));
        return processor.processFile(fp);
    }

    private static FileRead getReadFile(String file) throws FileIOException {
        FileInputParameter fp = new FileInputParameter(file);
        File sourceAsFile = fp.getSourceAsFile();
        sourceValidation(sourceAsFile);
        FileRead readFile = new FileRead();
        FileProcessor processor = new FileProcessor(readFile);
        processor.processFile(fp);
        return readFile;
    }

    private static void sourceValidation(File sourceFile) throws FileIOException {
        validateIfExists(sourceFile);
        validateIfCanRead(sourceFile);
    }

    private static void destinationValidation(File destinationFile) throws FileIOException {
        validateIfNotExists(destinationFile);
        validateIfCanWrite(destinationFile.getParentFile());
    }

}
