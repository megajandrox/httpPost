package commons.io.utils.strategy;

import commons.io.utils.exceptions.FileIOException;

import java.io.*;

public class FileWrite implements FileOperationStrategy {

    private String content;

    public FileWrite(String content) {
        this.content = content;
    }

    @Override
    public boolean execute(FileInputParameter fileParameter) throws FileIOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileParameter.getDestinationFile());
            fos.write(this.content.getBytes());
            return true;
        } catch (Exception e) {
            throw new FileIOException("Error writing file");
        } finally {
            try {
                if(fos != null) {
                    fos.close();
                }
            } catch(IOException e) {
                throw new FileIOException("Error closing file");
            }
        }
    }
}
