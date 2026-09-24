package business;

import java.util.Random;

/**
 * Class that is used to determine (get) random values
 */
public final class Luck {

    /**
     * final instance of the random java.utils class
     */
    private static final Random random = new Random();

    /**
     * constructor of Luck class
     */
    public Luck() {

    }

    /**
     * static function that returns a value between min and max time
     * @param minTime min value to return
     * @param maxTime max value to return
     * @return random value between min and max
     */
    public static int MoveInterval(int minTime, int maxTime) {
        int interval = random.nextInt(minTime, maxTime + 1);
        return interval * 1000;
    }

    /**
     * static function that returns the move change (if character is going to move)
     * @param chance threshold to determine if the character has to move or not
     * @return true if the value rolled i greater than the chance threshold
     */
    public static boolean MoveChance(int chance) {
        int roll = random.nextInt(1, 101);

        return roll < chance + 1;
    }

    /**
     * Function used to create a random integer with the specified bounds
     * @param length maximum value to return
     * @return random integer between 0 and length
     */
    public static int getRandomInt(int length) {
        return random.nextInt(0, length);
    }

    /**
     * static function that returns a value between min and max time
     * @param maxRoom max value to return
     * @return random value between min and max
     */
    public static int RandomRoom(int maxRoom){
        return random.nextInt(0, maxRoom + 1);
    }

    /**
     * static function that returns a value between min and max time
     * @param min min value to return
     * @param max max value to return
     * @return random value between min and max
     */
    public static int RandomPixel(int min, int max){
        return random.nextInt(min, max + 1);
    }
}
