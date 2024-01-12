package net.hydrius.hydriusjoin.util.action;

import java.util.ArrayList;
import java.util.List;

public class ActionUtils {

    public static List<Action> parseActions(List<String> section) {
        List<Action> actions = new ArrayList<>();
        if (section != null) {
            section.forEach(entry -> {
                Action action = parseAction(entry);
                if (action != null) {
                    actions.add(action);
                }
            });
        }
        return actions;
    }

    private static Action parseAction(String entry) {
        ActionType type = null;

        if (entry.startsWith("[")) {
            String actionString = entry.substring(1, entry.indexOf("]"));
            switch (actionString) {
                case "message":
                    type = ActionType.MESSAGE;
                    break;
                case "command":
                    type = ActionType.COMMAND;
                    break;
                case "effect":
                    type = ActionType.EFFECT;
                    break;
                case "broadcast":
                    type = ActionType.BROADCAST;
                    break;
                case "console":
                    type = ActionType.CONSOLE;
                    break;
                case "sound":
                    type = ActionType.SOUND;
                    break;
                case "title":
                    type = ActionType.TITLE;
                    break;
                case "actionbar":
                    type = ActionType.ACTIONBAR;
                    break;
            }
        }

        if (type != null) {
            return new Action(type, entry.substring(entry.indexOf("] ") + 2));
        }

        return null;
    }

}
