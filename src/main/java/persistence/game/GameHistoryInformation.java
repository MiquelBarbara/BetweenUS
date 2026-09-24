package persistence.game;

/**
 * Class used to get game history information of a user
 */
public class GameHistoryInformation {
    /**
     * Boolean that indicates if the game was win
     */
    private final boolean win;
    /**
     * float that indicates the accumulated win percentage
     */
    private final float winPercentage;

    /**
     * GameHistoryInformation constructor
     * @param win result of the game
     * @param winPercentage accumulated win percentage
     */
    public GameHistoryInformation(boolean win, float winPercentage) {
        this.win = win;
        this.winPercentage = winPercentage;
    }

    /**
     * Function used to check if the game was win
     * @return true if the game was win
     */
    public boolean isWin() {
        return win;
    }

    /**
     * Function used to get win percentage
     * @return win percentage
     */
    public float getWinPercentage() {
        return winPercentage;
    }
}
