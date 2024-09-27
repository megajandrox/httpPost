import java.io.File;
import java.io.FileOutputStream;

public class Main {

    public static void main(String[] args) {
        createRandomContentFile("file.txt", 1024);
    }

    //Creates a random content file
    public static File createRandomContentFile(String fileName, int size) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] buffer = new byte[size];
            for (int i = 0; i < size; i++) {
                buffer[i] = (byte) Math.round(Math.random() * 255);
            }
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File(fileName);
    }
}
