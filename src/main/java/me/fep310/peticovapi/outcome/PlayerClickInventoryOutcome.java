package me.fep310.peticovapi.outcome;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface PlayerClickInventoryOutcome {
    void onOutcome(Player player, boolean leftClick, ClickType clickType);
}
