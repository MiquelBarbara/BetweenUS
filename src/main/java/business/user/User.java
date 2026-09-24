package business.user;

/**
 * Class that represents a user logged into the program.
 * It is useful to play games, register account, delete account etc
 */
public class User {
    /**
     * Username of the user
     */
    private final String username;
    /**
     * email of the user
     */
    private final String eMail;
    /**
     * password of the user
     */
    private final String password;

    /**
     * Construtor of the User class
     * @param username username of the user to set
     * @param eMail email of the user to set
     * @param password password of the user to set
     */
    public User(String username, String eMail, String password) {
        this.username = username;
        this.eMail = eMail;
        this.password = password;
    }

    /**
     * function that returns user username
     * @return user's username
     */
    public String getUsername() {
        return username;
    }
    /**
     * function that returns user email
     * @return user's email
     */
    public String getEMail() {
        return eMail;
    }
    /**
     * function that returns user password
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

}
