package commons.db.utils.exceptions;

public class DDLActionException extends RuntimeException {
    public DDLActionException(String msgError) {
        super(msgError);
    }
}
