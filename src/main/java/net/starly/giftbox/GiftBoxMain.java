package net.starly.giftbox;

import org.bukkit.plugin.java.JavaPlugin;
import skyexcel.data.file.Config;

public class GiftBoxMain extends JavaPlugin {

    public static JavaPlugin plugin;
    public static Config config;

    @Override
    public void onEnable() {
        plugin = this;
        config = new Config("");
        config.setPlugin(this);
        config.loadDefaultPluginConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

