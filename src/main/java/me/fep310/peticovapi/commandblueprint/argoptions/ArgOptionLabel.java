package me.fep310.peticovapi.commandblueprint.argoptions;

import me.fep310.peticovapi.outcome.CommandOutcome;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArgOptionLabel extends ArgOption {

    private final String label;

    public ArgOptionLabel(int argIndex, CommandOutcome outcome, String label) {
        super(argIndex, outcome);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public ArgOptionType getEnumType() {
        return ArgOptionType.LABEL;
    }

    @Override
    public @NotNull List<String> getCompletions() {
        return List.of(label);
    }
}
