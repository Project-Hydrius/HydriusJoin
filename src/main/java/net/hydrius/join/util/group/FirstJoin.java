package net.hydrius.join.util.group;

import net.hydrius.join.util.action.Action;
import net.hydrius.join.util.action.ActionUtils;
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
