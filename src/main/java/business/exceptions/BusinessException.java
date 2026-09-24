package business.exceptions;

/**
 * Exception that is thrown when an error occurs on business layer
 */
public abstract class BusinessException  extends Exception{
    /**
     * Constructor of Business exception
     * @param message message to display
     */
    public BusinessException(String message) {
        super(message);
    }
}
