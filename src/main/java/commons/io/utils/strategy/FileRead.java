package commons.io.utils.strategy;

import commons.io.utils.exceptions.FileIOException;

import java.io.*;

public class FileRead implements FileOperationStrategy {

    private StringBuilder sb= new StringBuilder();;

    public String getFileContent() {
        return sb.toString();
    }

    public byte[] getBinaryFileContent() {
        return sb.toString().getBytes();
    }

    @Override
    public boolean execute(FileInputParameter fileParameter) throws FileIOException {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(fileParameter.getSourceFile());
            br = new BufferedReader(fr);
            String s;
            while((s=br.readLine()) != null) {
                sb.append(s).append("\n");
            }
        } catch(FileNotFoundException e) {
            throw new FileIOException("File not found");
        } catch (IOException e) {
            throw new FileIOException("Error reading file");
        } finally {
            try {
                if(fr != null) {
                    fr.close();
                }
                if(br != null) {
                    br.close();
                }
            } catch(IOException e) {
                throw new FileIOException("Error closing file");
            }
        }
        return true;
    }
}
