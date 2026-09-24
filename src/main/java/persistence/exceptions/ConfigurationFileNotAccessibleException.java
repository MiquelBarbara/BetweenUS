package persistence.exceptions;

public class ConfigurationFileNotAccessibleException extends PersistenceException{
    /**
     * Constructor of ConfigurationFIleNotAccessibleException
     */
    public ConfigurationFileNotAccessibleException() {
        super("ERROR: Configuration file can't not be accessed");
    }
}
