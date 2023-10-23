package net.hydrius.join.util.group;

import net.hydrius.join.util.action.Action;
import net.hydrius.join.util.action.ActionType;
import net.hydrius.join.util.action.ActionUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class JoinGroup {

    private final String name;
    private final int weight;
    private final int delay;
    private final List<Action> joinActions;
    private final List<Action> leaveActions;

    public JoinGroup(String name, ConfigurationSection section) {
        this.name = name;
        this.weight = section.getInt("weight");
        this.delay = section.getInt("delay") * 20;

        joinActions = ActionUtils.parseActions(section.getStringList("join-actions"));
        leaveActions = ActionUtils.parseActions(section.getStringList("leave-actions"));
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

    public List<Action> getJoinActions() {
        return joinActions;
    }

    public List<Action> getLeaveActions() {
        return leaveActions;
    }

}
