package business.exceptions;

/**
 * Exception that occurs when resources can not be accessed by business layer
 */
public class ResourcesNotAccessedException extends BusinessException {
    /**
     * Constructor of ResourcesNotAccessedException
     */
    public ResourcesNotAccessedException() {super("ERROR loading resources!\n");}
}
