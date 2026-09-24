package business.characters.roles;

public class CrewMate extends Role{
    /**
     * constructor of CrewMate role class
     */
    public CrewMate() {
        super(5, 15, 55);
        this.canKill = false;
    }
}
