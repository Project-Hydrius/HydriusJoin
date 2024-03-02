package net.hydrius.hydriusjoin.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import net.hydrius.hydriusjoin.HydriusJoin;
import net.hydrius.hydriusjoin.util.group.JoinGroup;
import net.hydrius.hydriusjoin.util.group.MotdGroup;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerListener implements Listener {

    private final HydriusJoin plugin;

    public PlayerListener(HydriusJoin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.joinMessage(null);

        // Clear inventory
        if(plugin.getConfig().getBoolean("items.settings.clear-inventory")) player.getInventory().clear();

        // Join
        if(!player.hasPlayedBefore() && plugin.getConfig().getBoolean("first-join.enabled")) {
            plugin.getGroupManager().getFirstJoin().getActions().forEach(action -> action.execute(player));
        } else {
            JoinGroup joinGroup = plugin.getGroupManager().getJoinGroup(player.getUniqueId());
            int joinDelay = Math.max(joinGroup.getDelay(), 0);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if(!joinGroup.getJoinActions().isEmpty()) joinGroup.getJoinActions().forEach(action -> action.execute(player));
            }, joinDelay);
        }

        // MOTD
        MotdGroup motdGroup = plugin.getGroupManager().getMotdGroup(player.getUniqueId());
        int motdDelay = Math.max(motdGroup.getDelay(), 0);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(!motdGroup.getActions().isEmpty()) motdGroup.getActions().forEach(action -> action.execute(player));
        }, motdDelay);

        // Set items
        plugin.getItemManager().getItemGroups().forEach((name, group) -> {
            if(!player.hasPermission("join.item." + name)) return;
            if(player.getInventory().getItem(group.getSlot()) != null && !plugin.getConfig().getBoolean("items.settings.override")) return;

            // check if the item is a player head and set the texture
            if(group.getItemStack().getType() == Material.PLAYER_HEAD) {
                SkullMeta meta = (SkullMeta) group.getItemStack().getItemMeta();
                String playerName = plugin.getConfig().getString("items.entries." + name + ".skin");
                if(PlaceholderAPI.containsPlaceholders(playerName)) playerName = PlaceholderAPI.setPlaceholders(player, playerName);
                Player target = Bukkit.getOfflinePlayer(playerName).getPlayer();
                meta.setOwningPlayer(target);
                group.getItemStack().setItemMeta(meta);
            }

            player.getInventory().setItem(group.getSlot(), group.getItemStack());
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.quitMessage(null);

        JoinGroup group = plugin.getGroupManager().getJoinGroup(player.getUniqueId());
        int delay = Math.max(group.getDelay(), 0);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(!group.getLeaveActions().isEmpty()) group.getLeaveActions().forEach(action -> action.execute(player));
        }, delay);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();

        if(plugin.getConfig().getBoolean("items.settings.allow-dropping")) return;
        if(plugin.getItemManager().getItemGroup(item) == null) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();

        if(!(inventory instanceof PlayerInventory)) return;

        int slot = event.getSlot();
        ItemStack item = inventory.getItem(slot);

        if(!plugin.getItemManager().getSlots().contains(slot)) return;
        if(plugin.getConfig().getBoolean("items.settings.allow-movement")) return;
        if(plugin.getItemManager().getItemGroup(item) == null) return;
        event.setCancelled(true);

        if(event.getClick() == ClickType.RIGHT) plugin.getItemManager().getItemGroup(item).getRightClickActions().forEach(action -> action.execute(player));
        if(event.getClick() == ClickType.MIDDLE) plugin.getItemManager().getItemGroup(item).getMiddleClickActions().forEach(action -> action.execute(player));
        if(event.getClick() == ClickType.LEFT) plugin.getItemManager().getItemGroup(item).getLeftClickActions().forEach(action -> action.execute(player));

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(item == null) return;
        if(plugin.getItemManager().getItemGroup(item) == null) return;
        event.setCancelled(true);

        if(event.getAction().name().contains("LEFT")) plugin.getItemManager().getItemGroup(item).getLeftClickActions().forEach(action -> action.execute(player));
        if(event.getAction().name().contains("MIDDLE")) plugin.getItemManager().getItemGroup(item).getMiddleClickActions().forEach(action -> action.execute(player));
        if(event.getAction().name().contains("RIGHT")) plugin.getItemManager().getItemGroup(item).getRightClickActions().forEach(action -> action.execute(player));
    }

}
