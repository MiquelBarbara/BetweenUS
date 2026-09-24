package persistence.user;

import business.user.User;
import persistence.exceptions.PersistenceException;

import java.util.ArrayList;

public interface UserDAO {
    /**
     * Function that adds a new user to the database
     * @param user New user to create and add to the database (user must contain:
     *              username, email, password)
     */
    int addUser(User user) throws PersistenceException;

    /**
     * Function that deletes a user from the database given its username
     * @param userID ID of the user
     */
    void deleteUser(int userID) throws PersistenceException;

    /**
     * Function that is used to get all users saved into database
     * @return Arraylist of all userNames stored into database
     */
    ArrayList<String> getUsers() throws PersistenceException;

    /**
     * Function used to update the user with matching username into database
     * @param user User instance to update values
     */
    void updateUser(User user) throws PersistenceException;

    /**
     * Function that is used to get the user with the matching username from the database
     * @param username Username of the user to get
     * @return Instance of User with matching username
     */
    User getUserByUsername(String username) throws PersistenceException;

    /**
     * Function that is used to get the user with the matching email from the database
     * @param email Email of the user to get
     * @return Instance of User with matching email
     */
    User getUserByEmail(String email) throws PersistenceException;

    /**
     * Function used to check if the given information string (username/email) and the password given
     * matches with the user stored
     * @param credential username/email of the user to log in
     * @param password password of the user to log in
     * @return true if the username/email matches with the password given of the user
     */
    boolean checkUser(String credential, String password) throws PersistenceException;

    /**
     * Function that checks if the email provided already exists in our database system
     * @param email String that contains the email to check
     * @return false id the email is not registered in our database
     */
    boolean emailAlreadyExists(String email) throws PersistenceException;

    /**
     * Function that given the email of a user, returns its ID stored in database
     * @param email email of the current user
     * @return ID of the user
     */
    int getUserID(String email) throws PersistenceException;

}