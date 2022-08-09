package me.fep310.peticovapi.outcome;

import me.fep310.peticovapi.util.PeticovUtil;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class OutcomeUtils {

    private static final Sound DEFAULT_SOUND = Sound.BLOCK_NOTE_BLOCK_BIT;
    private static final float DEFAULT_VOLUME = .8f;
    private static final float DEFAULT_PITCH = 1.25f;
    private static final float SUCCESS_PITCH = 1.75f;
    private static final float FAIL_PITCH = .75f;

    public static void success(CommandSender sender, @Nullable String comment) {
        if (comment != null)
            PeticovUtil.sendMessage(sender, "&a"+comment);

        tryPlaySound(sender, DEFAULT_SOUND, DEFAULT_VOLUME, SUCCESS_PITCH);
    }

    public static void warn(CommandSender sender, @Nullable String comment) {
        if (comment != null)
            PeticovUtil.sendMessage(sender, "&e"+comment);

        tryPlaySound(sender, DEFAULT_SOUND, DEFAULT_VOLUME, DEFAULT_PITCH);
    }

    public static void showRightUsage(CommandSender sender, @Nullable String comment, String rightUsage) {
        if (comment != null)
            PeticovUtil.sendMessage(sender, "&c"+comment);

        PeticovUtil.sendMessage(sender, "&6Right usage:&e "+rightUsage);
        tryPlaySound(sender, DEFAULT_SOUND, DEFAULT_VOLUME, DEFAULT_PITCH);
    }

    public static void noPermission(CommandSender sender) {
        PeticovUtil.sendMessage(sender, "&cYou don't have the permission to use this command.");
        tryPlaySound(sender, DEFAULT_SOUND, DEFAULT_VOLUME, FAIL_PITCH);
    }

    public static void fail(CommandSender sender, @Nullable String comment) {
        if (comment != null)
            PeticovUtil.sendMessage(sender, "&c"+comment);

        tryPlaySound(sender, DEFAULT_SOUND, DEFAULT_VOLUME, FAIL_PITCH);
    }

    private static void tryPlaySound(CommandSender sender, Sound sound, float volume, float pitch) {
        if (sender instanceof Player player) {
            player.playSound(player, sound, volume, pitch);
        }
    }
}
