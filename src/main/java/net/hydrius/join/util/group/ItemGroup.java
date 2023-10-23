package net.hydrius.join.util.group;

import dev.flrp.espresso.util.EnchantUtils;
import dev.flrp.espresso.util.StringUtils;
import net.hydrius.join.util.action.Action;
import net.hydrius.join.util.action.ActionUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemGroup {

    private final String name;
    private final ItemStack item;
    private final int slot;
    private final List<Action> leftClickActions;
    private final List<Action> rightClickActions;
    private final List<Action> middleClickActions;

    public ItemGroup(String name, ConfigurationSection section) {
        this.name = name;
        this.item = createItem(section);
        this.slot = section.getInt("slot");
        this.leftClickActions = ActionUtils.parseActions(section.getStringList("actions.left-click-actions"));
        this.rightClickActions = ActionUtils.parseActions(section.getStringList("actions.right-click-actions"));
        this.middleClickActions = ActionUtils.parseActions(section.getStringList("actions.middle-click-actions"));
    }

    private ItemStack createItem(ConfigurationSection section) {
        ItemStack itemStack = new ItemStack(Material.valueOf(section.getString(".material")));
        itemStack.setAmount(section.getInt("amount"));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(section.contains("name")) itemMeta.setDisplayName(StringUtils.parseColor(section.getString("name")));
        if(section.contains("lore")) itemMeta.setLore(StringUtils.parseColor(section.getStringList("lore")));
        if(section.contains("model-data")) itemMeta.setCustomModelData(section.getInt("model-data"));
        if(section.contains("enchantments")) {
            for(String enchantment : section.getStringList(".enchantments")) {
                String[] enchantmentSplit = enchantment.split(":");
                itemMeta.addEnchant(EnchantUtils.getEnchantByVersion(enchantmentSplit[0]), Integer.parseInt(enchantmentSplit[1]), true);
            }
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    public List<Action> getLeftClickActions() {
        return leftClickActions;
    }

    public List<Action> getRightClickActions() {
        return rightClickActions;
    }

    public List<Action> getMiddleClickActions() {
        return middleClickActions;
    }

}
