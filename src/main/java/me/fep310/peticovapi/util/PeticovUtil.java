package me.fep310.peticovapi.util;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class PeticovUtil {

    public static final List<String> teamStrings = Arrays.asList("red", "blue", "orange", "pink");

    public static ItemStack itemStack(Material material, int quantity, String name) {
        ItemStack itemStack = new ItemStack(material, quantity);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(text(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static TextComponent text(String content) {
        return Component.text(ChatColor.translateAlternateColorCodes('&', content));
    }

    public static @Nullable Integer stringToInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static @Nullable Double stringToDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static boolean isPlayerName(String s) {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(HumanEntity::getName)
                .anyMatch(s::equalsIgnoreCase);
    }

    public static Location defaultLocation() {
        return new Location(Bukkit.getServer().getWorlds().get(0), 0,0,0);
    }

    public static Location roundLocation(Location location) {

        Location newLocation = location.toCenterLocation();
        newLocation.setY(newLocation.getY() - .5);
        newLocation.setPitch(0);
        newLocation.setYaw(Math.round(newLocation.getYaw() / 90) * 90);

        return newLocation;
    }

    public static void sendMessage(Audience receiver, String message) {
        receiver.sendMessage(text(message));
    }

    public static void broadcastMessage(String message) {
        Bukkit.broadcast(text(message));
    }
}
