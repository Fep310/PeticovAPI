package me.fep310.peticovapi.itemstackbuilder;

import me.fep310.peticovapi.util.PeticovUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemStackBuilder {

    private final ItemStack itemStack;
    private ItemMeta itemMeta;
    private final List<Component> lore = new ArrayList<>();

    private ItemMeta getItemMeta() {
        if (itemMeta == null) {
            itemMeta = itemStack.getItemMeta();
        }
        return itemMeta;
    }

    public ItemStackBuilder() {
        itemStack = new ItemStack(Material.STONE, 1);
    }

    public ItemStackBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder setName(String name) {
        getItemMeta().displayName(PeticovUtil.text(name));
        return this;
    }

    public ItemStackBuilder addLore(String text) {
        lore.add(PeticovUtil.text(text));
        return this;
    }

    public ItemStackBuilder setUnbreakable(boolean b) {
        getItemMeta().setUnbreakable(b);
        return this;
    }

    public ItemStack get() {
        getItemMeta().lore(lore); // Apply lore
        itemStack.setItemMeta(getItemMeta()); // Apply ItemMeta
        return itemStack;
    }
}
