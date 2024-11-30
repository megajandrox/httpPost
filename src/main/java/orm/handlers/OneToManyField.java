package orm.handlers;

import com.http.post.model.Entity;
import orm.GenericDao;
import orm.mapping.OneToMany;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static orm.ReflexionUtils.getFieldFromHierarchy;

public class OneToManyField {

    public static synchronized <T> void handle(T entity, Class<?> type, BiConsumer<GenericDao, Entity> daoOperation)
            throws IllegalAccessException, SQLException, NoSuchFieldException {
        getFields(type)
                .forEach(f  -> callGenericDAO(entity, type, f, daoOperation));
    }

    private static <T> void callGenericDAO(T entity, Class<?> type, Field field, BiConsumer<GenericDao, Entity> daoOperation) {
        field.setAccessible(true);
        try {
            List<Entity> relatedEntities = (ArrayList<Entity>) field.get(entity);
            relatedEntities.stream().forEach(relatedEntity -> {
                try {
                    Field idField = getFieldFromHierarchy(type, "id");
                    idField.setAccessible(true);
                    long parentId = (long) idField.get(entity);
                    addForeignKeyField(field, relatedEntity, parentId);

                    GenericDao relatedDao = new GenericDao(relatedEntity.getClass());
                    daoOperation.accept(relatedDao, relatedEntity);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Field> getFields(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields()).filter(f -> f.getAnnotation(OneToMany.class) != null).collect(Collectors.toList());
    }

    private static void addForeignKeyField(Field f, Entity relatedEntity, long parentId) throws NoSuchFieldException, IllegalAccessException {
        String mappedBy = f.getAnnotation(OneToMany.class).mappedBy();
        Field foreignKeyField = getFieldFromHierarchy(relatedEntity.getClass(), mappedBy);
        foreignKeyField.setAccessible(true);
        foreignKeyField.set(relatedEntity, parentId);
    }
}
