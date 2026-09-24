package business.exceptions.format;

/**
 * Exception that occurs when there's an error on the email while user is trying to log in
 */
public class EMailException extends FormatException{
    /**
     * Constructor of EMailException
     */
    public EMailException() {
        super("The email is not correct. Please try again!");
    }
}
