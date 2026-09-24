package persistence.user;

import business.user.User;
import persistence.exceptions.PersistenceException;
import persistence.database.SQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DAO of User connected to DB
 */
public class SQLUserDAO implements UserDAO {
    /**
     * Constructor of SQLUserDAO
     */
    public SQLUserDAO() {

    }

    @Override
    public int addUser(User user) throws PersistenceException {
        int ID = generateUserID();
        String query ="INSERT INTO Usuari(ID_User, Name, Email, Password) VALUES ('" +
                ID + "', '" +
                user.getUsername() + "', '" +
                user.getEMail() + "', '" +
                user.getPassword() +
                "');";

        SQLConnector.getInstance().insertQuery(query);

        return ID;
    }

    private int generateUserID() throws PersistenceException {
        String query = "SELECT MAX(ID_User) as maxID FROM Usuari;";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);
        int id = 1;
        try {
            while (result.next()) {
                int maxID = result.getInt("maxID");
                id = maxID + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public ArrayList<String> getUsers() throws PersistenceException {
        ArrayList<String> users = new ArrayList<>();
        String query = "SELECT Name FROM Usuari;";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                String name = result.getString("name");
                users.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void deleteUser(int userID) throws PersistenceException {
        String query = "DELETE FROM Usuari WHERE ID_User = '" + userID + "';";
        SQLConnector.getInstance().deleteQuery(query);
    }

    @Override
    public void updateUser(User user) throws PersistenceException {
        String query = "UPDATE Usuari " +
                "SET name = '" + user.getUsername() + "', email = '" + user.getEMail() + "', password = '" + user.getPassword() +
                "' WHERE name = '" + user.getUsername() + "';";

        SQLConnector.getInstance().updateQuery(query);
    }

    @Override
    public User getUserByUsername(String Username) throws PersistenceException {
        String query = "SELECT ID_User, Name, Email, Password FROM Usuari WHERE name = '" + Username + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                int ID = result.getInt("ID_User");
                String name = result.getString("name");
                String email = result.getString("email");
                String password = result.getString("password");

                return new User(name, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String UserEmail) throws PersistenceException {
        String query = "SELECT ID_User, Name, Email, Password FROM Usuari WHERE email = '" + UserEmail + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {

            while (result.next()) {
                int ID = result.getInt("ID_User");
                String name = result.getString("Name");
                String email = result.getString("Email");
                String password = result.getString("Password");

                return new User(name, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean checkUser(String string, String password) throws PersistenceException {
        User user = getUser(string);
        if (user != null) {

            return user.getPassword().equals(password);
        }
        return false;
    }

    private User getUser(String string) throws PersistenceException {
        User user = getUserByUsername(string);
        if (user == null) {
            user = getUserByEmail(string);
        }

        return user;
    }

    @Override
    public boolean emailAlreadyExists(String email) throws PersistenceException {
        String query = "SELECT Count(Email) FROM Usuari WHERE email = '" + email + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            if (result.next()) {
                return result.getInt("count") >= 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public int getUserID(String credential) throws PersistenceException {
        String query = "SELECT ID_User FROM Usuari WHERE email = '" + credential + "' OR Name = '" + credential  + "' ;";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            if (result.next()) {
                return result.getInt("ID_User");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}