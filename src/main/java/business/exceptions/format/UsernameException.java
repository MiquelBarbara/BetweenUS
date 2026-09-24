package business.exceptions.format;

/**
 * Exception that occurs when the username provided by the user in order to be registered already exists
 */
public class UsernameException extends FormatException{
    /**
     * Constructor of UsernameException
     */
    public UsernameException() {
        super("This username already exists. Please try again!");
    }
}
