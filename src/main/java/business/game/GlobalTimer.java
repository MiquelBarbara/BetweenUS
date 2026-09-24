package business.game;

/**
 * Class used to represent the global timer of the game (normally refered to as INSTANT)
 */
public class GlobalTimer {
    /**
     * integer that indicates the actual amount of minutes
     */
    private static int minutes;
    /**
     * Integer that indicates the actual amount of seconds
     */
    private static int seconds;
    /**
     * Integer that indicates the amount of seconds passed since clock start
     */
    private static int instant;
    /**
     * Attribute used to implement the Thread of the timer
     */
    private static Thread timerThread;
    /**
     * Attribute used to determine if the timer is currently running
     */
    private static boolean isRunning;

    /**
     * Function that returns the actual time in the indicated format
     * @return String that represents the time
     */
    public static String getTime() {
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Function that resets the timer (clock)
     */
    public static void resetTimer() {
        minutes = 0;
        seconds = 0;
        instant = 0;
    }

    /**
     * Function that starts running the clock
     */
    public static void startTimer() {
        resetTimer();
        isRunning = true;

         timerThread = new Thread(() -> {
            try {
                while (isRunning) {
                    //Esperem 1 segon
                    Thread.sleep(1000);
                    seconds++;
                    instant++;
                    if (seconds == 60) {
                        seconds = 0;
                        minutes++;
                    }
                }
            } catch (InterruptedException e) {

            }
        });

        timerThread.start();
    }

    /**
     * Function used to return the instant (in seconds) of the clock
     * in fact it returns the total amount of seconds of the clock
     * @return total seconds passed since clock start
     */
    public static int getInstant() {
        return instant;
    }

    /**
     * Function used to stop the global timer thread
     */
    public static void stop() {
        isRunning = false;
        resetTimer();
        timerThread.interrupt();

    }
}
