package commons.db.utils.file;

import commons.db.utils.bussiness.exceptions.CreateException;
import commons.db.utils.bussiness.exceptions.DeletionException;
import commons.db.utils.bussiness.exceptions.SearchException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class FileManager<T> {

    private String objectName;

    public FileManager(String objectName) {
        this.objectName = objectName;
    }

    public void createEmptyFile() throws CreateException {
        try {
            File file = new File(objectName + ".dat");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new CreateException("There was an error creating the file");
        }
    }

    public List<T> readAll() throws SearchException {
        List<T> objectList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(objectName + ".dat")))) {
            int size = ois.readInt();
            for (int i = 0; i < size; i++) {
                T object = (T) ois.readObject();
                objectList.add(object);
            }
            System.out.println("Customers read from file successfully.");
        } catch (ClassNotFoundException e) {
            throw new SearchException("There was an error reading the file");
        } catch (IOException e) {
            try {
                createEmptyFile();
            } catch (CreateException ex) {
                throw new SearchException("There was an error reading the file");
            }
        }
        return objectList;
    }

    public void writeFile(List<T> objectList) throws CreateException {
        List<T> all = new ArrayList<>();
        try {
            all = readAll();
        } catch (SearchException e) {
            throw new RuntimeException(e);
        }
        all.addAll(objectList);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(objectName + ".dat")))) {
            oos.writeInt(all.size());
            for (T object : all) {
                oos.writeObject(object);
            }
            System.out.println("Customers written to file successfully.");
        } catch (IOException e) {
            throw new CreateException("There was an error writing to the file");
        }
    }

    public void removeObject(int id) throws DeletionException {
        try (RandomAccessFile raf = new RandomAccessFile(getObjectName() + ".dat", "rw")) {
            raf.seek(0);
            int size = raf.readInt();
            for (int i = 0; i < size; i++) {
                if (raf.readInt() == id) {
                    raf.seek(i * 12);
                    raf.writeInt(size - 1);
                    for (int j = i; j < size - 1; j++) {
                        raf.seek(j * 12);
                        raf.writeUTF(raf.readUTF());
                        raf.writeInt(raf.readInt());
                    }
                    break;
                }
            }
        } catch (IOException e) {
            throw new DeletionException("There was an error deleting the object");
        }
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
