package business.exceptions.format;

import business.exceptions.BusinessException;

/**
 * Exception that is thrown when there's an error on the form format
 */
public class FormatException extends BusinessException {
    /**
     * Constructor of FormatException
     */
    public FormatException(String message) {
        super(message);
    }
}
