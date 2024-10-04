package commons.db.utils.exceptions;

public class CreateTableRuntimeException extends DBRuntimeRuntimeException {
    public CreateTableRuntimeException(SQLActionException e) {
        super("There was an error creating the table", e);
    }
}
