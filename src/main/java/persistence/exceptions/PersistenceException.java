package persistence.exceptions;

/**
 * Exception that occurs when there's an error on persistence layer
 */
public abstract class PersistenceException extends Exception{
    /**
     * Constructor of persistenceException
     * @param message message to be displayed
     */
    public PersistenceException(String message) {
        super(message);
    }
}
