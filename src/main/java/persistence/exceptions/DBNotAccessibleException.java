package persistence.exceptions;

public class DBNotAccessibleException extends PersistenceException {
    /**
     * Constructor of DBNotAccessibleException
     */
    public DBNotAccessibleException() {
        super("ERROR: data base can't not be accessed");
    }
}
