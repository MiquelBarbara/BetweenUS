package business.characters.roles;

public class Impostor extends Role {
    /**
     * integer that indicates the role reload time (only for impostor)
     */
    private final int reloadTime = 25000;

    /**
     * Constructor of impostor role class
     */
    public Impostor() {
        super(6, 8, 45);
        this.canKill = true;
        this.roomLimits = false;
    }

    @Override
    public int getReloadTime() {
        return reloadTime;
    }
}
