package business.game.map.attributes;

public enum RoomType {
    ROOM("room"),
    PASS("pass"),
    VOID("void");

    public final String label;

    RoomType(String label) {
        this.label = label;
    }

    public static RoomType fromString(String text) {
        for (RoomType day : RoomType.values()) {
            if (day.label.equalsIgnoreCase(text)) {
                return day;
            }
        }
        return null;
    }
}
