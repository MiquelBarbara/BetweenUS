package business.characters.roles;

/**
 * Class used to represent the diferent role actions that a character can perform
 */
public class Role {
    /**
     * integer that indicates the minimum move time
     */
    private final int minMoveTime;
    /**
     * integer that indicates the maximum move time
     */
    private final int maxMoveTime;
    /**
     * integer that indicates the  move chance
     */
    private final int moveChance;
    /**
     * integer that indicates if the character can kill
     */
    protected boolean canKill;
    /**
     * integer that indicates if the character has room limits
     */
    protected boolean roomLimits;

    /**
     * Constructor of Role class
     * @param minMoveTime minimum moving time
     * @param maxMoveTime maximum moving time
     * @param moveChance move chance
     */
    public Role(int minMoveTime, int maxMoveTime, int moveChance) {
        this.minMoveTime = minMoveTime;
        this.maxMoveTime = maxMoveTime;
        this.moveChance = moveChance;
        this.roomLimits = true;
        this.canKill = false;
    }

    /**
     * Function that gets the minimum moving time
     * @return minimum moving time
     */
    public int getMinMoveTime() {
        return minMoveTime;
    }
    /**
     * Function that gets the maximum moving time
     * @return maximum moving time
     */
    public int getMaxMoveTime() {
        return maxMoveTime;
    }
    /**
     * Function that gets the role's move chance
     * @return role's moving chance
     */
    public int getMoveChance() {
        return moveChance;
    }

    /**
     * Function that checks if the role is able to kill
     * @return true if the role can kill
     */
    public boolean canKill() {
        return canKill;
    }

    /**
     * Function that checks if the role has any room limitation
     * @return true if the role has a room limitation
     */
    public boolean hasRoomLimits() {
        return roomLimits;
    }

    /**
     * Function that gets role reload time
     * @return role's reload time
     */
    public int getReloadTime() {
        return 0;
    }
}
