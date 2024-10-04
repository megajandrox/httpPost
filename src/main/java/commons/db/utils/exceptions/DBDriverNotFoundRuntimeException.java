package commons.db.utils.exceptions;

public class DBDriverNotFoundRuntimeException extends DBRuntimeRuntimeException {

    public DBDriverNotFoundRuntimeException(ClassNotFoundException e) {
        super(e);
    }
}
