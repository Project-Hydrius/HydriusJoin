package net.hydrius.hydriusjoin.manager;

import net.hydrius.hydriusjoin.HydriusJoin;
import net.hydrius.hydriusjoin.util.group.FirstJoin;
import net.hydrius.hydriusjoin.util.group.JoinGroup;
import net.hydrius.hydriusjoin.util.group.MotdGroup;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class GroupManager {

    private final FirstJoin firstJoin;
    private final HashMap<String, JoinGroup> joinGroup = new HashMap<>();
    private final HashMap<String, MotdGroup> motdGroup = new HashMap<>();

    public GroupManager(HydriusJoin plugin) {
        firstJoin = new FirstJoin(plugin.getConfig().getConfigurationSection("first-join"));
        Set<String> joinGroupNames = plugin.getConfig().getConfigurationSection("formats").getKeys(false);
        joinGroupNames.forEach(groupName -> {
            joinGroup.put(groupName, new JoinGroup(groupName, plugin.getConfig().getConfigurationSection("formats." + groupName)));
        });
        Set<String> motdGroupNames = plugin.getConfig().getConfigurationSection("motds").getKeys(false);
        motdGroupNames.forEach(groupName -> {
            motdGroup.put(groupName, new MotdGroup(groupName, plugin.getConfig().getConfigurationSection("motds." + groupName)));
        });
    }

    public FirstJoin getFirstJoin() {
        return firstJoin;
    }

    public JoinGroup getJoinGroup(String name) {
        return joinGroup.get(name);
    }

    public HashMap<String, JoinGroup> getJoinGroups() {
        return joinGroup;
    }

    public JoinGroup getJoinGroup(UUID uuid) {
        Set<PermissionAttachmentInfo> infoSet = Bukkit.getPlayer(uuid).getEffectivePermissions();
        String group = "default";
        int weight = 0;
        for(PermissionAttachmentInfo info : infoSet) {
            if(!info.getPermission().startsWith("hydriusjoin.group.")) continue;
            String g = info.getPermission().substring("hydriusjoin.group.".length());
            if(!joinGroup.containsKey(g)) continue;
            int w = joinGroup.get(g).getWeight();
            if(w < weight) continue;
            group = g;
            weight = w;
        }
        return joinGroup.get(group);
    }

    public MotdGroup getMotdGroup(String name) {
        return motdGroup.get(name);
    }

    public HashMap<String, MotdGroup> getMotdGroups() {
        return motdGroup;
    }

    public MotdGroup getMotdGroup(UUID uuid) {
        Set<PermissionAttachmentInfo> infoSet = Bukkit.getPlayer(uuid).getEffectivePermissions();
        String group = "default";
        int weight = 0;
        for(PermissionAttachmentInfo info : infoSet) {
            if(!info.getPermission().startsWith("hydriusjoin.motd.")) continue;
            String g = info.getPermission().substring("hydriusjoin.motd.".length());
            if(!motdGroup.containsKey(g)) continue;
            int w = motdGroup.get(g).getWeight();
            if(w < weight) continue;
            group = g;
            weight = w;
        }
        return motdGroup.get(group);
    }

}
