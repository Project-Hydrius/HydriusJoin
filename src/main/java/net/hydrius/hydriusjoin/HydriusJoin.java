package net.hydrius.hydriusjoin;

import dev.flrp.espresso.configuration.Configuration;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import net.hydrius.hydriusjoin.commands.DefaultCommand;
import net.hydrius.hydriusjoin.listener.PlayerListener;
import net.hydrius.hydriusjoin.manager.GroupManager;
import net.hydrius.hydriusjoin.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class HydriusJoin extends JavaPlugin {

    private GroupManager groupManager;
    private ItemManager itemManager;

    private Location spawnPoint;

    @Override
    public void onEnable() {
        Configuration config = new Configuration(this, "config");
        config.load();
        spawnPoint = getLocationFromConfig();
        groupManager = new GroupManager(this);
        itemManager = new ItemManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(this);
        commandManager.registerCommand(new DefaultCommand(this));
    }

    public void onReload() {
        Configuration config = new Configuration(this, "config");
        config.load();
        spawnPoint = getLocationFromConfig();
        groupManager = new GroupManager(this);
        itemManager = new ItemManager(this);
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    private Location getLocationFromConfig() {
        if(!getConfig().contains("settings.spawn-location")) return null;
        String world = getConfig().getString("settings.spawn-location.world");
        double x = getConfig().getDouble("settings.spawn-location.x");
        double y = getConfig().getDouble("settings.spawn-location.y");
        double z = getConfig().getDouble("settings.spawn-location.z");
        float yaw = (float) getConfig().getDouble("settings.spawn-location.yaw");
        float pitch = (float) getConfig().getDouble("settings.spawn-location.pitch");
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

}
