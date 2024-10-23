package file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.http.post.model.Request;
import com.http.post.utils.bussiness.exceptions.CreateException;
import com.http.post.utils.bussiness.exceptions.DeletionException;
import com.http.post.utils.bussiness.exceptions.SearchException;
import com.http.post.utils.bussiness.exceptions.UpdateException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class JsonManager<T> {

    private String objectName;

    public JsonManager(String objectName) {
        this.objectName = objectName;
    }

    private void createEmptyFile() throws CreateException {
        try {
            File file = new File(objectName + ".json");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new CreateException("There was an error creating the file");
        }
    }

    public <T> List<T>  readAll(Class<T> clazz) throws SearchException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(objectName + ".json"), new TypeReference<List<T>>() {
                @Override
                public Type getType() {
                    return objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
                }
            });
        } catch (IOException e) {
            try {
                createEmptyFile();
            } catch (CreateException ex) {
                throw new SearchException("There was an error reading the file");
            }
        }
        return new ArrayList<>();
    }

    public void write(List<T> objectList, Class<T> clazz) throws CreateException {
        List<T> all;
        try {
            all = readAll(clazz);
        } catch (SearchException e) {
            throw new RuntimeException(e);
        }
        all.addAll(objectList);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(objectName + ".json"), all);
        } catch (IOException e) {
            throw new CreateException("There was an error writing to the file");
        }
    }

    public void removeObject(List<T> objects, Predicate<T> criteria) throws DeletionException {
        ObjectMapper objectMapper = new ObjectMapper();
        objects.removeIf(criteria);
        try {
            objectMapper.writeValue(new File(objectName + ".json"), objects);
        } catch (IOException e) {
            throw new DeletionException("There was an error deleting the object");
        }
    }


    public  void updateObject(T object, Class<T> clazz, Predicate<T> criteria) throws UpdateException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonFile = objectName + ".json";
        try {
            List<T> objects = readAll(clazz);
            Optional<T> first = objects.stream().filter(criteria).findFirst();
            if (first.isPresent()) {
                objects.removeIf(criteria);
                objects.add(object);
            }
            objectMapper.writeValue(new File(jsonFile), objects);
        } catch (IOException | SearchException e) {
            throw new UpdateException("There was an error updating the object");
        }
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
