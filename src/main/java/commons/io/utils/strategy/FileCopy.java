package commons.io.utils.strategy;

import commons.io.utils.exceptions.FileIOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy implements FileOperationStrategy {

    @Override
    public boolean execute(FileInputParameter fileParameter) throws FileIOException {
        File sourceFile = new File(fileParameter.getSourceFile());
        File destinationFile = new File(fileParameter.getDestinationFile());
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            return true;
        } catch (Exception e) {
            throw new FileIOException("Error copying file");
        } finally {
            try {
                if(fis != null) {
                    fis.close();
                }
                if(fos != null) {
                    fos.close();
                }
            } catch(IOException e) {
                throw new FileIOException("Error closing file");
            }
        }
    }
}
