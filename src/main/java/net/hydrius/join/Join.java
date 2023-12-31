package net.hydrius.join;

import dev.flrp.espresso.configuration.Configuration;
import net.hydrius.join.listener.PlayerListener;
import net.hydrius.join.manager.GroupManager;
import net.hydrius.join.manager.ItemManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Join extends JavaPlugin {

    private GroupManager groupManager;
    private ItemManager itemManager;

    @Override
    public void onEnable() {
        Configuration config = new Configuration(this, "config");
        config.load();
        groupManager = new GroupManager(this);
        itemManager = new ItemManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

}
