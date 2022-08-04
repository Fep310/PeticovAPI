package me.fep310.peticovapi.datafile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public abstract class PluginDataFile {

    protected File file;
    protected FileConfiguration configFile;
    protected final String fileName;
    protected final Plugin plugin;

    public PluginDataFile(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        setup();
    }

    /**
     * Sets up the PluginDataFile. It's called inside the constructor.
     */
    private void setup() {

        File pluginDataFolder = new File(Bukkit.getPluginsFolder().getPath() + "/" + plugin.getName());

        if (pluginDataFolder.mkdirs())
            Bukkit.getServer().getConsoleSender().sendMessage(
                    ChatColor.GREEN+"Successfully created the plugin's data folder.");

        file = new File(pluginDataFolder, fileName);

        if (!file.exists()) {
            try {
                if (file.createNewFile()){
                    Bukkit.getServer().getConsoleSender().sendMessage(
                            ChatColor.GREEN+"Successfully created the data.yml file.");
                }
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(
                        ChatColor.RED+""+e.getMessage());
            }
        }

        configFile = YamlConfiguration.loadConfiguration(file);

        setupDefaults();
    }

    /**
     * Sets up the default values inside the PluginDataFile. It's called every setup().
     * This method should be overwritten for every extension of the PluginDataFile class so that the desired
     * defaults are correctly set. You should always call the super() method on the last line of the overwritten
     * method.
     */
    protected void setupDefaults() {
        configFile.options().copyDefaults(true);
        save();
    }

    /**
     * Saves any change that has been done to the PluginDataFile.
     */
    protected final void save() {
        try {
            configFile.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(
                    ChatColor.RED+""+e.getMessage());
        }
    }

    /**
     * Reloads the PluginDataFile and applies any changes done inside the .yml file itself.
     */
    public final void reload() {
        configFile = YamlConfiguration.loadConfiguration(file);
    }
}
