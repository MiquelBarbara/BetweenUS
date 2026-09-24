/**
 * The BetweenUs program implements a game very similar to the AmongUs
 *
 * @author  Miquel Barbarà, Ainhoa Corominas, Marc Joan Sabater, Oleguer Almuni and David Casadó
 * @version 1.0
 * @since   2023-05-28
 */

import presentation.Controller;

/**
 * This is the main class of the program, is the one is called when running the program.
 */
public class Main {
    /**
     * This is the main method which function is create the necessary classes to call the controller.
     * @param args Unused.
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.run();
    }
}
