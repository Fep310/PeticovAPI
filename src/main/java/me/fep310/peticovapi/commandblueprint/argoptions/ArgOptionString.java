package me.fep310.peticovapi.commandblueprint.argoptions;

import me.fep310.peticovapi.outcome.CommandOutcome;

import java.util.List;

public class ArgOptionString extends ArgOption {

    public ArgOptionString(int argIndex, CommandOutcome outcome) {
        super(argIndex, outcome);
    }

    @Override
    public ArgOptionType getEnumType() {
        return ArgOptionType.STRING;
    }

    @Override
    public List<String> getCompletions() {
        return List.of();
    }
}
