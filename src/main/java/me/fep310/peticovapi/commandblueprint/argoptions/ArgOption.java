package me.fep310.peticovapi.commandblueprint.argoptions;

import me.fep310.peticovapi.outcome.CommandOutcome;
import me.fep310.peticovapi.outcome.OutcomeUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ArgOption {

    // TODO: Multiple types of constructors to avoid the repetitive assignment of null.

    protected int argIndex;
    protected CommandOutcome outcome;
    protected @Nullable String[] prevArgs;
    protected @Nullable String permission;
    private @Nullable String description;
    private @Nullable String nextPossibleArgs;

    public int getArgIndex() {
        return argIndex;
    }
    public @Nullable String[] getPreviousArgs() {
        return prevArgs;
    }
    public @Nullable String getPermission() { return permission; }
    public @Nullable String getDescription() { return description; }

    public @Nullable String getNextPossibleArgs() {
        return nextPossibleArgs;
    }

    public ArgOption(int argIndex, CommandOutcome outcome){
        this.argIndex = argIndex;
        this.outcome = outcome;
    }

    public ArgOption setPrevArgs(String[] prevArgs) {
        this.prevArgs = prevArgs;
        return this;
    }

    public ArgOption setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public ArgOption setDescription(String description) {
        this.description = description;
        return this;
    }

    public ArgOption setNextPossibleArgs(String nextPossibleArgs) {
        this.nextPossibleArgs = nextPossibleArgs;
        return this;
    }

    abstract public ArgOptionType getEnumType();

    abstract public List<String> getCompletions();

    public void doOutcome(CommandSender sender, String[] args) {

        if (permission != null && !sender.hasPermission(permission)) {
            OutcomeUtils.noPermission(sender);
            return;
        }

        outcome.onOutcome(sender, args);
    }

}
