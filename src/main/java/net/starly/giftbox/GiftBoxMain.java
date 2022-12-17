package net.starly.giftbox;

import net.starly.core.data.Config;
import net.starly.giftbox.cmd.GiftBoxCmd;
import net.starly.giftbox.cmd.GiftBoxTabComplete;
import net.starly.giftbox.event.GiftBoxListener;
import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;


public class GiftBoxMain extends JavaPlugin {

    private static JavaPlugin plugin;

    public static Config config;

    @Override
    public void onEnable() {
        plugin = this;

        //Command
        Bukkit.getPluginCommand("선물함").setExecutor(new GiftBoxCmd());
        Bukkit.getPluginCommand("선물함").setTabCompleter(new GiftBoxTabComplete());

        //Listener
        Bukkit.getPluginManager().registerEvents(new GiftBoxListener(), plugin);

        //Config
        config = new Config("config");
        config.loadDefaultPluginConfig();
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}

