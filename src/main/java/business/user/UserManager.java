package business.user;

import business.exceptions.BusinessException;
import business.exceptions.LogInException;
import business.exceptions.ResourcesNotAccessedException;
import business.exceptions.format.*;
import persistence.game.gameconfig.GameConfigDAO;
import persistence.game.GameDAO;
import persistence.exceptions.PersistenceException;
import persistence.game.gameconfig.SQLGameConfigDAO;
import persistence.game.SQLGameDAO;
import persistence.user.SQLUserDAO;
import persistence.user.UserDAO;

/**
 * Class used to manage user operations (CRUD)
 */
public class UserManager {

    /**
     * DAO of the user to persist information
     */
    private final UserDAO userDAO;

    /**
     * Constructor of UserManager class
     */
    public UserManager(){
        userDAO = new SQLUserDAO();
    }

    /**
     * This function creates and safes a new User to the database
     *
     * @param credentials array of strings that contains the email of the user in the
     *                    first position and the password associated in the second
     * @return the id of the newly generated user.
     * @throws FormatException will jump if the username already exists in the database
     * @throws ResourcesNotAccessedException will jump if there's and error on saving resources
     */
    public int createUser(String[] credentials) throws FormatException, ResourcesNotAccessedException {
        checkUsername(credentials[0]);
        checkEMail(credentials[1]);
        checkPassword(credentials[2]);
        checkPasswordEqual(credentials[2], credentials[3]);
        User user = new User(credentials[0], credentials[1], credentials[2]);
        try {
            return userDAO.addUser(user); // return the ID of the created user
        } catch (PersistenceException e) {
            //throw new ResourcesNotAccessedException();
        }
        return 0;
    }

    /**
     *Function that check if the username provided is already in the database
     * @param username that we want to check
     * @exception UsernameException will jump if the name already exists
     * @throws ResourcesNotAccessedException will jump if there's and error on checking resources
     */
    public void checkUsername(String username) throws UsernameException, ResourcesNotAccessedException {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null) {
                throw new UsernameException();
            }
        } catch (PersistenceException e) {
            throw new ResourcesNotAccessedException();
        }

    }

    /**
     *Function that checks if  the eMail contains the correct format
     * @param eMail that we want to check
     * @exception EMailException will jump if one of the requirements is not presented
     */
    public void checkEMail(String eMail) throws EMailException {
        if(!(eMail.contains("@gmail.com") || eMail.contains("@hotmail.com") || eMail.contains("@students.salle.url.edu"))){
            throw new EMailException();
        }

    }

    /**
     *Function that checks if the password contains 8 characters which includes MINIMUM a mayus letter, a min letter and a number
     * @param password that we want to check
     * @exception PasswordException will jump if one of the requirements is not presented
     */
    public void checkPassword(String password) throws PasswordException {
        if(!(password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z]*.") && password.matches(".*\\d+.*"))){
            throw new PasswordException();
        }

    }

    /**
     * Function that checks if the two password provided are equal in order to perform a correct registration
     * @param password password provided
     * @param password2 password confirmation
     * @throws PasswordNotEqualException occurs when the two passwords aren't equal
     */
    public void checkPasswordEqual(String password, String password2) throws PasswordNotEqualException {
        if(!(password.equals(password2))){
            throw new PasswordNotEqualException();
        }

    }

    /**
     * Function that log in a user given its credentials
     * @param credentials String[] that contains the user email and the password. In this order
     * @throws BusinessException occurs when a user can not log in
     */
    public int logIn(String[] credentials) throws BusinessException {
        try {
            if (!userDAO.checkUser(credentials[0], credentials[1])) {
                throw new LogInException();
            }
            // On login we setup the userID we're operating on
            int userID = userDAO.getUserID(credentials[0]);

            return userID;
        } catch (PersistenceException e) {
            throw new ResourcesNotAccessedException();
        }
    }

    /**
     * Function used to delete the acount of the user with matching ID
     * @param userID ID of the user to delete account
     * @throws ResourcesNotAccessedException occurs when the user account can not be deleted
     */
    public void deleteUserAccount(int userID) throws ResourcesNotAccessedException {
        try {
            GameDAO gameDAO = new SQLGameDAO();
            GameConfigDAO gameConfigDAO = new SQLGameConfigDAO();
            gameDAO.deleteUserGames(userID);
            gameConfigDAO.deleteUserConfigurations(userID);
            userDAO.deleteUser(userID);
        } catch (PersistenceException e) {
            throw new ResourcesNotAccessedException();
        }
    }




}