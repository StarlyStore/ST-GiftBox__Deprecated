package net.starly.giftbox;

import net.starly.giftbox.cmd.GiftBoxCmd;
import net.starly.giftbox.cmd.GiftBoxTab;
import net.starly.giftbox.event.GiftBoxListener;
import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import skyexcel.data.file.Config;


public class GiftBoxMain extends JavaPlugin {

    public static JavaPlugin plugin;

    public static Config config;

    @Override
    public void onEnable() {
        plugin = this;
        getCommand("선물함").setExecutor(new GiftBoxCmd());
        getCommand("선물함").setTabCompleter(new GiftBoxTab());
        Bukkit.getPluginManager().registerEvents(new GiftBoxListener(), this);
        config = new Config("config");
        config.setPlugin(this);
        config.loadDefaultPluginConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}

