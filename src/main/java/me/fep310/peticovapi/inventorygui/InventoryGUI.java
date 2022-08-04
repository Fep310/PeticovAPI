package me.fep310.peticovapi.inventorygui;

import me.fep310.peticovapi.outcome.PlayerOutcome;
import me.fep310.peticovapi.util.PeticovUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class InventoryGUI implements Listener {

    private final Inventory inventory;
    private final Component title;
    private final Map<Integer, PlayerOutcome> clickIndexOutcomes;

    public InventoryGUI(InventoryType type, String title) {
        this.title = PeticovUtil.text(title);
        inventory = Bukkit.createInventory(null, type, this.title);
        clickIndexOutcomes = new HashMap<>();
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    protected void setBackground(ItemStack itemStack, BackgroundFillType fillType) throws UnsupportedOperationException {
        switch (fillType){
            case FULL -> fillInventory(itemStack);
            default -> throw new UnsupportedOperationException("BackgroundFillType filling method not implemented yet.");
        }
    }

    protected void addClickableItem(int index, ItemStack itemStack, PlayerOutcome outcome) {
        clickIndexOutcomes.put(index, outcome);
        inventory.setItem(index, itemStack);
    }

    private void fillInventory(ItemStack itemStack) {
        ItemStack[] contents = new ItemStack[inventory.getSize()];
        Arrays.fill(contents, itemStack);
        inventory.setContents(contents);
    }

    // TODO: Put all InventoryClickEvents into a single Listener class
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        InventoryView view = event.getView();

        if (!view.title().equals(title))
            return;

        event.setCancelled(true);

        int slot = event.getSlot();

        if (clickIndexOutcomes.containsKey(slot)) {
            clickIndexOutcomes.get(slot).onOutcome((Player) view.getPlayer());
            view.close();
        }
    }
}
