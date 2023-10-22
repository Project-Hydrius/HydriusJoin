package net.hydrius.join.listener;

import net.hydrius.join.Join;
import net.hydrius.join.util.group.JoinGroup;
import net.hydrius.join.util.group.MotdGroup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final Join plugin;

    public PlayerListener(Join plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(!player.hasPlayedBefore() && plugin.getConfig().getBoolean("first-join.enabled")) {
            plugin.getGroupManager().getFirstJoin().getActions().forEach(action -> action.execute(player));
        } else {
            JoinGroup joinGroup = plugin.getGroupManager().getJoinGroup(player.getUniqueId());
            int joinDelay = Math.max(joinGroup.getDelay(), 0);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if(!joinGroup.getJoinActions().isEmpty()) joinGroup.getJoinActions().forEach(action -> action.execute(player));
            }, joinDelay);
        }
        MotdGroup motdGroup = plugin.getGroupManager().getMotdGroup(player.getUniqueId());
        int motdDelay = Math.max(motdGroup.getDelay(), 0);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(!motdGroup.getActions().isEmpty()) motdGroup.getActions().forEach(action -> action.execute(player));
        }, motdDelay);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        JoinGroup group = plugin.getGroupManager().getJoinGroup(player.getUniqueId());
        int delay = Math.max(group.getDelay(), 0);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(!group.getLeaveActions().isEmpty()) group.getLeaveActions().forEach(action -> action.execute(player));
        }, delay);
    }

}
