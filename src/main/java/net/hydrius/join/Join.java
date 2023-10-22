package net.hydrius.join;

import dev.flrp.espresso.configuration.Configuration;
import net.hydrius.join.listener.PlayerListener;
import net.hydrius.join.manager.GroupManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Join extends JavaPlugin {

    private static Join instance;

    private GroupManager groupManager;

    @Override
    public void onEnable() {
        instance = this;
        Configuration config = new Configuration(this, "config");
        config.load();
        groupManager = new GroupManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public static Join getInstance() {
        return instance;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

}
