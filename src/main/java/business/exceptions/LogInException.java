package business.exceptions;

/**
 * Exception that occurs when there's an error during user's login
 */
public class LogInException extends BusinessException{
    /**
     * Constructor of LogInException
     */
    public LogInException() {
        super("Invalid credentials. Please try again!");
    }
}
