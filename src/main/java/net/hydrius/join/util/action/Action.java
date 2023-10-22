package net.hydrius.join.util.action;

import dev.flrp.espresso.util.StringUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Action {

    ActionType type;
    String value;

    public Action(ActionType type, String value) {
        this.type = type;
        this.value = value;
    }

    public void execute(Player player) {
        String value = this.value;
        switch(type) {
            case MESSAGE:
                value = PlaceholderAPI.setPlaceholders(player, value);
                player.sendMessage(StringUtils.parseColor(value));
                break;
            case COMMAND:
                value = PlaceholderAPI.setPlaceholders(player, value);
                player.performCommand(value);
                break;
            case EFFECT:
                break;
            case BROADCAST:
                value = PlaceholderAPI.setPlaceholders(player, value);
                String finalValue = value;
                Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(StringUtils.parseColor(finalValue)));
                break;
            case CONSOLE:
                value = PlaceholderAPI.setPlaceholders(player, value);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), value);
                break;
        }
    }



}
