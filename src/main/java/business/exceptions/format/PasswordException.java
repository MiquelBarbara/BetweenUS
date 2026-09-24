package business.exceptions.format;

/**
 * Exception that occurs when the password selected by the user doesn't reach the format specified
 */
public class PasswordException extends FormatException{
    /**
     * Constructor of PasswordException
     */
    public PasswordException() {
        super("The password must have at least 8 characters, upper and lower case letters and numbers. Please try again!");
    }
}
