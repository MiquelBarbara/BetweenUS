package business.exceptions.format;

/**
 * Exception that occurs when the 2 passwords provided by the user are not equal
 */
public class PasswordNotEqualException extends FormatException{
    /**
     * Constructor of PasswordNotEqualException
     */
    public PasswordNotEqualException() {
        super("Passwords must be equal. Please try again!");
    }
}
