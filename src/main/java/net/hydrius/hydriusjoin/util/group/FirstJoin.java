package net.hydrius.hydriusjoin.util.group;

import net.hydrius.hydriusjoin.util.action.Action;
import net.hydrius.hydriusjoin.util.action.ActionUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class FirstJoin {

    List<Action> actions;

    public FirstJoin(ConfigurationSection section) {
        actions = ActionUtils.parseActions(section.getStringList("actions"));
    }

    public List<Action> getActions() {
        return actions;
    }

}
