package net.hydrius.hydriusjoin.commands;

import dev.flrp.espresso.util.StringUtils;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import net.hydrius.hydriusjoin.HydriusJoin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(value = "hydriusjoin", alias = {"hj", "join"})
public class DefaultCommand extends BaseCommand {

    private final HydriusJoin plugin;

    public DefaultCommand(HydriusJoin plugin) {
        this.plugin = plugin;
    }

    @Default
    public void defaultCommand(final CommandSender sender) {
        sender.sendMessage(StringUtils.parseColor("&bHydriusJoin &7v1.0.1 &8- &7Created by flrp"));
        sender.sendMessage(StringUtils.parseColor("&7Type &b/join help &7for a list of commands."));
    }

    @SubCommand("help")
    public void helpCommand(final CommandSender sender) {
        sender.sendMessage(StringUtils.parseColor("&bHydriusJoin &7v1.0.1 &8- &7Created by flrp"));
        sender.sendMessage(StringUtils.parseColor("&f/join help &8- &7Display this help message."));
        sender.sendMessage(StringUtils.parseColor("&f/join reload &8- &7Reload the plugin."));
        sender.sendMessage(StringUtils.parseColor("&f/join setspawn &8- &7Set the player spawn."));
    }

    @SubCommand("reload")
    @Permission("hydriusjoin.admin")
    public void reloadCommand(final CommandSender sender) {
        sender.sendMessage(StringUtils.parseColor("&7Reloading HydriusJoin..."));
        plugin.onReload();
        sender.sendMessage(StringUtils.parseColor("&aHydriusJoin has been reloaded."));
    }

    @SubCommand("setspawn")
    @Permission("hydriusjoin.admin")
    public void setSpawnCommand(final CommandSender sender) {
        if (!(sender instanceof Player)) return;
        String world = ((Player) sender).getLocation().getWorld().getName();
        double x = ((Player) sender).getLocation().getX();
        double y = ((Player) sender).getLocation().getY();
        double z = ((Player) sender).getLocation().getZ();
        double yaw = ((Player) sender).getLocation().getYaw();
        double pitch = ((Player) sender).getLocation().getPitch();
        plugin.getConfig().set("settings.spawn-location.world", world);
        plugin.getConfig().set("settings.spawn-location.x", x);
        plugin.getConfig().set("settings.spawn-location.y", y);
        plugin.getConfig().set("settings.spawn-location.z", z);
        plugin.getConfig().set("settings.spawn-location.yaw", yaw);
        plugin.getConfig().set("settings.spawn-location.pitch", pitch);
        plugin.saveConfig();
        plugin.setSpawnPoint(((Player) sender).getLocation());
        sender.sendMessage(StringUtils.parseColor("&aSpawn point has been set."));
    }

}
