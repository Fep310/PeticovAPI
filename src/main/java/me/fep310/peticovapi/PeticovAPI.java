package me.fep310.peticovapi;

import me.fep310.peticovapi.gui.GUIClickListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PeticovAPI extends JavaPlugin {

    private static PeticovAPI instance;
    public static PeticovAPI getInstance() {
        return instance;
    }

    public GUIClickListener clickListener;

    public GUIClickListener getGUIClickListener() {
        return clickListener;
    }

    @Override
    public void onEnable() {
        instance = this;
        clickListener = new GUIClickListener(this);
    }

    @Override
    public void onDisable() {}

}
