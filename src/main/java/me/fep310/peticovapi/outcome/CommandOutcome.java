package me.fep310.peticovapi.outcome;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

public interface CommandOutcome {
    void onOutcome(CommandSender sender, @Nullable String[] args);
}
