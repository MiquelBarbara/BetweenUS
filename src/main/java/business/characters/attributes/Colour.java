package business.characters.attributes;

/**
 * Enum to represent character's colour
 */
public enum Colour {
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    PINK("pink"),
    ORANGE("orange"),
    YELLOW("yellow"),
    BLACK("black"),
    WHITE("white"),
    PURPLE("purple"),
    BROWN("brown"),
    CYAN("cyan"),
    LIME("lime");
    /**
     * label of the enum
     */
    public final String label;

    Colour(String label) {
        this.label = label;
    }

    private static final Colour[] colours = values();
    public static Colour[] getColours() {
        return colours;
    }

    /**
     * Function used to returr the Specified colour given its message value
     * @param value value that indicates the colour value in rgb
     * @return Colour label
     */
    public static Colour fromString(String value) {
        return switch (value) {
            case "java.awt.Color[r=255,g=0,b=0]", "red" -> RED;
            case "java.awt.Color[r=0,g=0,b=255]", "blue" -> BLUE;
            case "java.awt.Color[r=0,g=255,b=0]", "green" -> GREEN;
            case "java.awt.Color[r=255,g=0,b=128]", "pink" -> PINK;
            case "java.awt.Color[r=255,g=200,b=0]", "orange" -> ORANGE;
            case "java.awt.Color[r=255,g=255,b=0]", "yellow" -> YELLOW;
            case "java.awt.Color[r=0,g=0,b=0]", "black" -> BLACK;
            case "java.awt.Color[r=255,g=255,b=255]", "white" -> WHITE;
            case "java.awt.Color[r=177,g=77,b=186]", "purple" -> PURPLE;
            case "java.awt.Color[r=128,g=64,b=0]", "brown" -> BROWN;
            case "java.awt.Color[r=0,g=255,b=255]", "cyan" -> CYAN;
            case "java.awt.Color[r=191,g=255,b=0]", "lime" -> LIME;

            default ->
                    throw new IllegalArgumentException("No se encontró un color válido con el valor especificado: " + value);
        };
    }
}

