package persistence.database;

/**
 * Class used to represent the configuration file
 */
public class ConfigurationFile {
    /**
     * Selected port of attached to DB
     */
    private int port;
    /**
     * IP attached to the DB
     */
    private String IP;
    /**
     * name of the DB
     */
    private String name;
    /**
     * Username of the user accessing to DB
     */
    private String userName;
    /**
     * password of the user accessing to DB
     */
    private String password;

    /**
     * Constructor of ConfigurationFile class
     */
    public ConfigurationFile() {
    }

    /**
     * Function that returns the port of the DB
     * @return port of the DB
     */
    public int getPort() {
        return port;
    }

    /**
     * Function that returns the IP of the DB
     * @return IP of the DB
     */
    public String getIp() {
        return IP;
    }
    /**
     * Function that returns the name of the DB
     * @return name of the DB
     */
    public String getName() {
        return name;
    }
    /**
     * Function that returns the username of the user
     * @return username of the user
     */
    public String getUser() {
        return userName;
    }
    /**
     * Function that returns the pasword of the user
     * @return password of the use
     */
    public String getPassword() {
        return password;
    }
}
