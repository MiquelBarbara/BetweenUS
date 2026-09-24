package persistence.exceptions;
public class mapLoadingException extends PersistenceException {
    /**
     * Constructor of mapLoadingException
     */
    public mapLoadingException() {
        super("ERROR: map resources can not be loaded!");
    }
}
