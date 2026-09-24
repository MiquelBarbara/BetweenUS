package business.exceptions;

public class ThreadException extends BusinessException{
    public ThreadException() {
        super("Error on thread running");
    }
}
