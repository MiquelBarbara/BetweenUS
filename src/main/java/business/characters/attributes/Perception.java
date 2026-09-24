package business.characters.attributes;

/**
 * Enum used to indicate the character's perception
 */
public enum Perception {
    INNOCENT("innocent"),
    SUS("sus"),
    UNKNOWN("unknown");

    /**
     * label of the enum
     */
    public final String label;

    private Perception(String label) {
        this.label = label;
    }

    /**
     * Function used to get the character's default perception of the user
     * @return label of the user's default perception on the character
     */
    public static String getDefaultPerception() {
        return UNKNOWN.label;
    }

    public static Perception fromString(String perception) {
        return switch (perception) {
            case "innocent" -> INNOCENT;
            case "sus" -> SUS;
            case "unknown" -> UNKNOWN;
            default ->
                    throw new IllegalArgumentException("No se encontró una percepcion válida con el valor especificado: " + perception);
        };
    }
}
