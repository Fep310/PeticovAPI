package me.fep310.peticovapi.util;

import me.fep310.peticovapi.commandblueprint.CommandBlueprint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Setup { // Configurator? Setuper?

    public static void aCommand(CommandBlueprint command, JavaPlugin plugin, boolean printToConsole) {

        String cmdName = command.getName();
        ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();

        PluginCommand pluginCommand = plugin.getCommand(cmdName);
        if (pluginCommand == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(
                    ChatColor.RED+"The command \""+cmdName+"\" was not found.");
            return;
        }

        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);

        String cmdRightUsage = command.getRightUsage();
        String cmdPermission = command.getPermission();

        if (cmdPermission == null)
            cmdPermission = "None";

        if (!printToConsole)
            return;

        sender.sendMessage("A command blueprint has been registered ->");
        sender.sendMessage("\tCommand name:\t\t"+cmdName);
        sender.sendMessage("\tCommand right usage:\t"+cmdRightUsage);
        sender.sendMessage("\tCommand permission:\t"+cmdPermission);
    }

    public static void aListener(Listener listener, JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
