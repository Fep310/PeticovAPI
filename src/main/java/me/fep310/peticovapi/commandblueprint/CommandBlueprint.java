package me.fep310.peticovapi.commandblueprint;

import me.fep310.peticovapi.commandblueprint.argoptions.*;
import me.fep310.peticovapi.outcome.CommandOutcome;
import me.fep310.peticovapi.outcome.OutcomeUtils;
import me.fep310.peticovapi.util.PeticovUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommandBlueprint implements CommandExecutor, TabCompleter {

    // Private members
    private final @NotNull String name;
    private @NotNull String rightUsage;
    private @Nullable String permission;
    private boolean isPlayerExclusive;
    private HelpType helpType;
    private CommandOutcome firstOutcome;
    private final List<ArgOption> argOptions;
    private final List<String> possibleLabels;

    // Public getters
    public @NotNull String getName() {
        return name;
    }
    public @NotNull String getRightUsage() {
        return rightUsage;
    }
    public @Nullable String getPermission() {
        return permission;
    }

    // Constructor
    public CommandBlueprint(@NotNull String name) {
        this.name = name;
        rightUsage = "/"+name;
        permission = name;
        isPlayerExclusive = false;
        helpType = HelpType.BOTH;
        possibleLabels = new ArrayList<>();
        argOptions = new ArrayList<>();

    }

    // Protected Builder Methods
    protected void setRightUsage(@NotNull String rightUsage) {
        this.rightUsage = rightUsage;
    }

    protected void setPermission(@Nullable String permission) {
        this.permission = permission;
    }

    protected void setPlayerExclusive(boolean isPlayerExclusive) {
        this.isPlayerExclusive = isPlayerExclusive;
    }

    public void setHelpType(HelpType helpType) {
        this.helpType = helpType;

        if (helpType == HelpType.AS_ARGUMENT || helpType == HelpType.BOTH) {
            setFirstOutcome((sender, args) -> showHelp(sender));
        }
    }
    protected void setFirstOutcome(@NotNull CommandOutcome firstOutcome) {
        this.firstOutcome = firstOutcome;
    }

    protected ArgOptionInt addArgOptionInt(int argIndex, CommandOutcome outcome) {
        ArgOptionInt arg = new ArgOptionInt(argIndex, outcome);
        argOptions.add(arg);
        return arg;
    }

    protected ArgOptionLabel addArgOptionLabel(int argIndex, String label, CommandOutcome outcome) {
        ArgOptionLabel arg = new ArgOptionLabel(argIndex, outcome, label);
        possibleLabels.add(label);
        argOptions.add(arg);
        return arg;
    }

    protected ArgOptionPlayer addArgOptionPlayer(int argIndex, CommandOutcome action) {
        ArgOptionPlayer arg = new ArgOptionPlayer(argIndex, action);
        argOptions.add(arg);
        return arg;
    }

    protected ArgOptionString addArgOptionString(int argIndex, CommandOutcome outcome) {
        ArgOptionString arg = new ArgOptionString(argIndex, outcome);
        argOptions.add(arg);
        return arg;
    }

    // Private Helper Methods

    private void showHelp(CommandSender sender) {

        if (argOptions.isEmpty()) {
            OutcomeUtils.fail(sender, "This command doesn't have any arguments.");
            return;
        }

        List<ArgOption> describedArgs = argOptions
                .stream()
                .filter(a -> a.getDescription() != null)
                .filter(a -> {
                    if (a.getPermission() == null)
                        return true;
                    return sender.hasPermission(a.getPermission());
                })
                .toList();

        if (describedArgs.isEmpty()) {
            OutcomeUtils.fail(sender, "No arguments with description with your permissions were found.");
            return;
        }

        PeticovUtil.sendMessage(sender, "&6- - - - - - &e/"+name+" &6- - - - - -");

        if (helpType == HelpType.AS_ARGUMENT || helpType == HelpType.BOTH)
            PeticovUtil.sendMessage(sender,
                    "&b/" + name + " help&7 Lists all possible uses of this command.");

        for (ArgOption describedArg : describedArgs) {

            StringBuilder rightUsageBuilder = new StringBuilder("&b/").append(name); // &b/name

            String[] prevArgs = describedArg.getPreviousArgs();
            if (prevArgs != null && prevArgs.length > 0) {
                rightUsageBuilder.append(" ").append(String.join(" ", prevArgs));
            }

            if (describedArg instanceof ArgOptionLabel stringArg) {
                rightUsageBuilder.append(" ").append(stringArg.getLabel());
            }

            String nextArgs = describedArg.getNextPossibleArgs();
            if (nextArgs != null) {
                rightUsageBuilder.append(" ").append(nextArgs);
            }

            PeticovUtil.sendMessage(sender, rightUsageBuilder+"&7 "+describedArg.getDescription());
        }

        // TODO: Adaptive line length with string builder
        PeticovUtil.sendMessage(sender, "&6- - - - - - - - - - - - - - - - -");

        OutcomeUtils.success(sender, null);
    }
    private ArgOptionType getArgumentType(String argLabel) {

        if (PeticovUtil.stringToInteger(argLabel) != null)
            return ArgOptionType.INTEGER;

        else if (PeticovUtil.stringToDouble(argLabel) != null)
            return ArgOptionType.DOUBLE;

        else if (PeticovUtil.isPlayerName(argLabel))
            return ArgOptionType.PLAYER_NAME;

        else if (possibleLabels.contains(argLabel))
            return ArgOptionType.LABEL;

        else
            return ArgOptionType.STRING;
    }
    private @Nullable ArgOption getArgument(int argIndex, String argLabel, String[] prevArgs, ArgOptionType argType) {

        if (argOptions.isEmpty())
            return null;

        List<ArgOption> filteredArgs = argOptions
                .stream()
                .filter(a -> a.getArgIndex() == argIndex)
                .filter(a -> {

                    if (a.getPreviousArgs() == null)
                        return prevArgs.length == 0;

                    List<String> prevArgsList = Arrays.stream(a.getPreviousArgs()).toList();
                    List<String> _prevArgsList = new ArrayList<>(Arrays.asList(prevArgs));

                    _prevArgsList.replaceAll(s -> {
                        if (PeticovUtil.isPlayerName(s))
                            return "player_name";
                        return s;
                    });

                    return _prevArgsList.equals(prevArgsList);
                })
                .filter(a -> a.getEnumType() == argType)
                .toList();

        if (filteredArgs.size() == 1)
            return filteredArgs.get(0);

        return filteredArgs
                .stream()
                .map(a -> (ArgOptionLabel)a)
                .filter(argOptionLabel -> argOptionLabel.getLabel().equalsIgnoreCase(argLabel))
                .findFirst()
                .orElse(null);
    }
    private @Nullable List<String> getCompletions(int argIndex, String[] prevArgs, CommandSender sender) {

        if (argOptions.isEmpty())
            return null;

        List<String> completions = new ArrayList<>();

        argOptions
                .stream()
                .filter(a -> {
                    if (a.getPermission() == null)
                        return true;
                    return sender.hasPermission(a.getPermission());
                })
                .filter(a -> a.getArgIndex() == argIndex)
                .filter(a -> {

                    if (a.getPreviousArgs() == null)
                        return prevArgs.length == 0;

                    List<String> prevArgsList = Arrays.stream(a.getPreviousArgs()).toList();
                    List<String> _prevArgsList = new ArrayList<>(Arrays.asList(prevArgs));

                    _prevArgsList.replaceAll(s -> {
                        if (PeticovUtil.isPlayerName(s))
                            return "player_name";
                        return s;
                    });

                    return _prevArgsList.equals(prevArgsList);
                })
                .forEach(argOption -> completions.addAll(
                        argOption instanceof ArgOptionString argOptionString ?
                                argOptionString.getCompletions(sender) :
                                argOption.getCompletions()));

        if (argIndex == 0 && (helpType == HelpType.AS_ARGUMENT || helpType == HelpType.BOTH))
            completions.add("help");

        return completions;
    }


    // Overrides (CommandExecutor and TabCompleter)
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (isPlayerExclusive) {
            if (!(sender instanceof Player)) {
                PeticovUtil.sendMessage(sender, "&cThis command should only be used by players.");
                return true;
            }
        }

        if (permission != null) {
            if (!sender.hasPermission(permission)) {
                OutcomeUtils.noPermission(sender);
                return true;
            }
        }

        int argsAmount = args.length;
        int argIndex = argsAmount - 1;

        if (argsAmount < 1 || argOptions.isEmpty()) {

            if (helpType == HelpType.AS_FIRST_ACTION || helpType == HelpType.BOTH) {
                showHelp(sender);
                return true;
            }

            firstOutcome.onOutcome(sender, args);
            return true;
        }

        String[] prevArgs;
        if (argsAmount == 1) {
            prevArgs = new String[0];
            if (args[0].equalsIgnoreCase("help") &&
                    (helpType == HelpType.AS_ARGUMENT || helpType == HelpType.BOTH)) {
                showHelp(sender);
                return true;
            }
        } else {
            int prevArgsAmount = argsAmount - 1;
            prevArgs = new String[prevArgsAmount];
            System.arraycopy(args, 0, prevArgs, 0, prevArgsAmount);
        }
        String currentArg = args[argIndex];
        ArgOptionType argumentType = getArgumentType(currentArg);

        ArgOption argOption = getArgument(argIndex, currentArg, prevArgs, argumentType);

        if (argOption == null) {
            OutcomeUtils.showRightUsage(sender, "Argument not found.", rightUsage);
            return true;
        }

        argOption.doOutcome(sender, args);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (permission != null) {
            if (!sender.hasPermission(permission))
                return List.of();
        }

        int argsAmount = args.length;
        int argIndex = argsAmount - 1;
        String[] prevArgs;
        if (argsAmount == 1) {
            prevArgs = new String[0];
        } else {
            int prevArgsAmount = argsAmount - 1;
            prevArgs = new String[prevArgsAmount];
            System.arraycopy(args, 0, prevArgs, 0, prevArgsAmount);
        }

        List<String> completions = getCompletions(argIndex, prevArgs, sender);

        return completions == null ? List.of() : completions;
    }
}
