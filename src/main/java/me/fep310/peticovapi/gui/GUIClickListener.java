package me.fep310.peticovapi.gui;

import me.fep310.peticovapi.outcome.PlayerClickInventoryOutcome;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class GUIClickListener implements Listener {

    private final Map<Component, Map<Integer, PlayerClickInventoryOutcome>> outcomesMap = new HashMap<>();

    public void addOutcome(Component title, int index, PlayerClickInventoryOutcome outcome) {
        if (!outcomesMap.containsKey(title))
            outcomesMap.put(title, new HashMap<>());
        outcomesMap.get(title).put(index, outcome);
    }

    public GUIClickListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        InventoryView view = event.getView();
        Component title = view.title();

        if (!outcomesMap.containsKey(title))
            return;

        event.setCancelled(true);

        Map<Integer, PlayerClickInventoryOutcome> pageOutcomesMap = outcomesMap.get(title);

        int slot = event.getSlot();

        if (!pageOutcomesMap.containsKey(slot))
            return;

        pageOutcomesMap.get(slot).onOutcome((Player) view.getPlayer(), event.isLeftClick(), event.getClick());
    }
}
