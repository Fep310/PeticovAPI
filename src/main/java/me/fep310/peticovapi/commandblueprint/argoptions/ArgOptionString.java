package me.fep310.peticovapi.commandblueprint.argoptions;

import me.fep310.peticovapi.outcome.CommandOutcome;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ArgOptionString extends ArgOption {

    private List<String> completions;
    private FindCompletions findCompletions;

    public ArgOptionString(int argIndex, CommandOutcome outcome) {
        super(argIndex, outcome);
    }

    @Override
    public ArgOptionType getEnumType() {
        return ArgOptionType.STRING;
    }

    @Override
    public List<String> getCompletions() {

        if (completions == null)
            completions = new ArrayList<>();

        return completions;
    }

    public List<String> getCompletions(CommandSender sender) {

        if (findCompletions != null)
            return findCompletions.onFindCompletions(sender);

        return getCompletions();
    }

    public ArgOptionString addPossibleCompletions(List<String> newCompletions) {

        if (completions == null)
            completions = new ArrayList<>();

        completions.addAll(newCompletions);

        return this;
    }

    public ArgOptionString addPossibleCompletions(FindCompletions findCompletions) {
        this.findCompletions = findCompletions;
        return this;
    }
}
