package net.hydrius.join.util.group;

import net.hydrius.join.util.action.Action;
import net.hydrius.join.util.action.ActionUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class MotdGroup {

    private final String name;
    private final int weight;
    private final int delay;
    private final List<Action> actions;

    public MotdGroup(String name, ConfigurationSection section) {
        this.name = name;
        this.weight = section.getInt("weight");
        this.delay = section.getInt("delay") * 20;

        actions = ActionUtils.parseActions(section.getStringList("actions"));
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getDelay() {
        return delay;
    }

    public List<Action> getActions() {
        return actions;
    }

}
