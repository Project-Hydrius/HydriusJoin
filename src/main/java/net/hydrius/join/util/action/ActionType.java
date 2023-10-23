package net.hydrius.join.util.action;

public enum ActionType {

    COMMAND,
    MESSAGE,
    EFFECT,
    BROADCAST,
    CONSOLE,
    SOUND,
    TITLE,
    ACTIONBAR;

    public static ActionType getByName(String type) {
        for(ActionType t : values()) {
            if(t.name().equalsIgnoreCase(type)) {
                return t;
            }
        }
        return null;
    }

}
