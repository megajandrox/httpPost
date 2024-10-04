package commons.db.utils.exceptions;

public class DropTableRuntimeException extends DBRuntimeRuntimeException {
    public DropTableRuntimeException(SQLActionException e) {
        super("There was an error dropping the table", e);
    }
}
