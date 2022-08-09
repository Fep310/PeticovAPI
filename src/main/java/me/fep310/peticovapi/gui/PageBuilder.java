package me.fep310.peticovapi.gui;

import me.fep310.peticovapi.PeticovAPI;
import me.fep310.peticovapi.outcome.PlayerClickInventoryOutcome;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PageBuilder {

    private final Page page;

    public PageBuilder() {
        page = new Page();
    }

    public PageBuilder setTitle(Component title) {
        page.title = title;
        return this;
    }

    public PageBuilder setSize(InventoryType inventoryType) {
        page.inventoryType = inventoryType;
        return this;
    }

    public PageBuilder setBackground(ItemStack itemStack) {
        page.setBackground(itemStack);
        return this;
    }

    public PageBuilder setClickableItem(ItemStack itemStack, int index, PlayerClickInventoryOutcome outcome) {
        page.setClickableItem(itemStack, index, outcome);
        return this;
    }

    public Page get() {
        page.registerPage();
        return page;
    }

    public static class Page {

        private Inventory inventory;
        private Inventory getInventory() {
            if (inventory == null)
                inventory = Bukkit.createInventory(null, inventoryType, title);
            return inventory;
        }
        private Component title;
        private InventoryType inventoryType;
        private final Map<Integer, PlayerClickInventoryOutcome> clickIndexOutcomes = new HashMap<>();

        private Page() {}

        private void setBackground(ItemStack itemStack) {
            ItemStack[] contents = new ItemStack[getInventory().getSize()];
            Arrays.fill(contents, itemStack);
            getInventory().setContents(contents);
        }

        private void setClickableItem(ItemStack itemStack, int index, PlayerClickInventoryOutcome outcome) {
            clickIndexOutcomes.put(index, outcome);
            getInventory().setItem(index, itemStack);
        }

        private void registerPage() {
            GUIClickListener clickListener = PeticovAPI.getInstance().getGUIClickListener();
            for (Map.Entry<Integer, PlayerClickInventoryOutcome> entry : clickIndexOutcomes.entrySet()) {
                clickListener.addOutcome(title, entry.getKey(), entry.getValue());
            }
        }

        public void open(Player player) {
            player.openInventory(inventory);
        }
    }
}
