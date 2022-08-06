package me.fep310.peticovapi.commandblueprint.argoptions;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface FindCompletions {
    List<String> onFindCompletions(CommandSender sender);
}
