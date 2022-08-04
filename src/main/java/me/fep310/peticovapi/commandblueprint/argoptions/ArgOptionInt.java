package me.fep310.peticovapi.commandblueprint.argoptions;

import me.fep310.peticovapi.outcome.CommandOutcome;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArgOptionInt extends ArgOption {


    public ArgOptionInt(int argIndex, CommandOutcome outcome) {
        super(argIndex, outcome);
    }

    @Override
    public ArgOptionType getEnumType() {
        return ArgOptionType.INTEGER;
    }

    @Override
    public @NotNull List<String> getCompletions() {
        return List.of();
    }
}
