package commons.io.utils;

import commons.io.utils.exceptions.FileIOException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FileUtilsTest {

    @Test
    public void copy() throws FileIOException {
        FileUtils.getInstance().copy("C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\fileCopy2.txt", "C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\fileCopy.txt");

    }

    @Test
    public void copyBinary() throws FileIOException {
        FileUtils.getInstance().copy("C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\er_diagram.png", "C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\er_diagram_COPY.png");
    }

    @Test
    public void move() throws FileIOException {
        FileUtils.getInstance().move("C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\file.txt", "C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\fileCopy.txt");

    }

    @Test
    public void moveBinary() throws FileIOException {
        FileUtils.getInstance().move("C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\er_diagram.png", "C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\er_diagram_COPY.png");
    }

    @Test
    public void read() throws FileIOException {
        String read = FileUtils.getInstance().read("C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\fileCopy.txt");
        assertTrue(read.length() > 0);
    }

    @Test
    public void readBinary() throws FileIOException {
        byte[] read = FileUtils.getInstance().readBinary("C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\er_diagram_COPY.png");
        assert read.length > 0;
    }

    @Test
    public void delete() throws FileIOException {
        FileUtils.getInstance().delete("C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\fileCopy.txt");
    }

    @Test
    public void deleteBinary() throws FileIOException {
        FileUtils.getInstance().move("C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\er_diagram.png", "C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\er_diagram_COPY.png");
    }

    @Test
    public void addContent() throws FileIOException {
        FileUtils.getInstance().addContent("C:\\Users\\Usuario\\IdeaProjects\\HttpPost\\src\\test\\resources\\fileCopy2.txt", "HOLA AMIGO");
    }
}