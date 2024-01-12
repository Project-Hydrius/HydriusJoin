package net.hydrius.hydriusjoin.manager;

import net.hydrius.hydriusjoin.HydriusJoin;
import net.hydrius.hydriusjoin.util.group.ItemGroup;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ItemManager {

    private final List<Integer> slots = new ArrayList<>();
    private final HashMap<String, ItemGroup> itemGroups = new HashMap<>();

    public ItemManager(HydriusJoin plugin) {
        Set<String> itemGroupNames = plugin.getConfig().getConfigurationSection("items.entries").getKeys(false);
        itemGroupNames.forEach(groupName -> {
            ItemGroup group = new ItemGroup(groupName, plugin.getConfig().getConfigurationSection("items.entries." + groupName));
            itemGroups.put(groupName, group);
            slots.add(group.getSlot());
        });
    }

    public ItemGroup getItemGroup(String name) {
        return itemGroups.get(name);
    }

    public HashMap<String, ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public ItemGroup getItemGroup(int slot) {
        for(ItemGroup group : itemGroups.values()) {
            if(group.getSlot() == slot) return group;
        }
        return null;
    }

    public ItemGroup getItemGroup(ItemStack itemStack) {
        for(ItemGroup group : itemGroups.values()) {
            if(group.getItemStack().isSimilar(itemStack)) return group;
        }
        return null;
    }

    public List<Integer> getSlots() {
        return slots;
    }

}
