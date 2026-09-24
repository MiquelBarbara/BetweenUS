package persistence.database;

import persistence.exceptions.PersistenceException;
import persistence.exceptions.DBNotAccessibleException;

import java.sql.*;

/**
 * The SQLConnector class will abstract the specifics of the connection to a MySQL database.
 *
 * This class follows the Singleton design pattern to facilitate outside access while maintaining
 * a single instance, as having multiple connectors to a database is generally discouraged.
 *
 * Be aware that this class presents a simplified approach. Configuration parameters SHOULD NOT be
 * hardcoded and the use of Statements COULD be replaced by PreparedStatements to avoid SQL Injection.
 */
public class SQLConnector {

    // The static attribute to implement the singleton design pattern.
    private static SQLConnector instance = null;


    /**
     * Static method that returns the shared instance managed by the singleton.
     *
     * @return The shared SQLConnector instance.
     */
    public static SQLConnector getInstance() throws PersistenceException {
        if (instance == null ){
            // NOT a good practice to hardcode connection data! Be aware of this for your project delivery ;)
            ConfigurationDAO configurationDAO = new ConfigurationDAO();
            ConfigurationFile configurationFile = configurationDAO.readFile();

            instance = new SQLConnector(configurationFile.getUser(), configurationFile.getPassword(), configurationFile.getIp(), configurationFile.getPort(), configurationFile.getName());
            instance.connect();
        }
        return instance;
    }

    /**
     * String that indicates db username
     */
    private final String username;
    /**
     * String that indicates db password
     */
    private final String password;
    /**
     * String that indicates db url
     */
    private final String url;
    /**
     * String that indicates db Connection
     */
    private Connection conn;

    // Parametrized constructor
    private SQLConnector(String username, String password, String ip, int port, String database) {
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + ip + ":" + port + "/" + database;
    }


    /**
     * Method that starts the inner connection to the database. Ideally, users would disconnect after
     * using the shared instance.
     */
    public void connect() throws DBNotAccessibleException {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch(SQLException e) {
            throw new DBNotAccessibleException();
        }
    }


    /**
     * Method that executes an insertion query to the connected database.
     *
     * @param query String representation of the query to execute.
     */
    public void insertQuery(String query) throws DBNotAccessibleException {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            throw new DBNotAccessibleException();
        }
    }


    /**
     * Method that executes an update query to the connected database.
     *
     * @param query String representation of the query to execute.
     */
    public void updateQuery(String query) throws DBNotAccessibleException {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            throw new DBNotAccessibleException();
        }
    }


    /**
     * Method that executes a deletion query to the connected database.
     *
     * @param query String representation of the query to execute.
     */
    public void deleteQuery(String query) throws DBNotAccessibleException {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            throw new DBNotAccessibleException();
        }

    }


    /**
     * Method that executes a selection query to the connected database.
     *
     * @param query String representation of the query to execute.
     * @return The results of the selection.
     */
    public ResultSet selectQuery(String query) throws DBNotAccessibleException {
        ResultSet rs = null;
        try {
            Statement s = conn.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            throw new DBNotAccessibleException();
        }
        return rs;
    }


    /**
     * Method that closes the inner connection to the database. Ideally, users would disconnect after
     * using the shared instance.
     */
    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem when closing the connection --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }
}
