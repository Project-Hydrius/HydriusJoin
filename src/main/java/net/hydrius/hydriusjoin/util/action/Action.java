package net.hydrius.hydriusjoin.util.action;

import dev.flrp.espresso.util.StringUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
                String[] effectSplit = value.split(":");
                if(PotionEffectType.getByName(effectSplit[0]) == null) return;
                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effectSplit[0]), Integer.parseInt(effectSplit[2]) * 20, Integer.parseInt(effectSplit[1])));
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
            case SOUND:
                String[] soundSplit = value.split(":");
                player.playSound(player.getLocation(), soundSplit[0], Float.parseFloat(soundSplit[1]), Float.parseFloat(soundSplit[2]));
                break;
            case TITLE:
                String[] titleSplit = value.split(":");
                player.sendTitle(StringUtils.parseColor(titleSplit[0]), StringUtils.parseColor(titleSplit[1]), Integer.parseInt(titleSplit[2]), Integer.parseInt(titleSplit[3]), Integer.parseInt(titleSplit[4]));
                break;
            case ACTIONBAR:
                value = PlaceholderAPI.setPlaceholders(player, value);
                Component component = Component.text(StringUtils.parseColor(value));
                player.sendActionBar(component);
                break;
        }
    }



}
