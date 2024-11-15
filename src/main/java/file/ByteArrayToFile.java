package file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteArrayToFile {

    public static File writeFile(byte[] content) throws IOException {
        File tempFile = File.createTempFile("tempFile", ".img");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(content);
        } catch (IOException e) {
            System.err.println("Failed to save file: " + e.getMessage());
        }
        return tempFile;
    }
}
