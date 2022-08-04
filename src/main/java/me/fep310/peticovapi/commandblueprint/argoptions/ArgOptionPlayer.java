package me.fep310.peticovapi.commandblueprint.argoptions;

import me.fep310.peticovapi.outcome.CommandOutcome;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

import java.util.List;

public class ArgOptionPlayer extends ArgOption {

    public ArgOptionPlayer(int argIndex, CommandOutcome outcome) {
        super(argIndex, outcome);
    }

    @Override
    public ArgOptionType getEnumType() {
        return ArgOptionType.PLAYER_NAME;
    }

    @Override
    public List<String> getCompletions() {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(HumanEntity::getName)
                .toList();
    }
}
