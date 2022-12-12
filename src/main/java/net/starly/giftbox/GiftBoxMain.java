package net.starly.giftbox;

import net.starly.giftbox.cmd.GiftBoxCmd;
import net.starly.giftbox.cmd.GiftBoxTab;
import net.starly.giftbox.event.GiftBoxListener;
import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import skyexcel.data.file.Config;


public class GiftBoxMain extends JavaPlugin {

    private static JavaPlugin plugin;

    public static Config config;

    @Override
    public void onEnable() {
        plugin = this;

        //Command
        Bukkit.getPluginCommand("선물함").setExecutor(new GiftBoxCmd());
        Bukkit.getPluginCommand("선물함").setTabCompleter(new GiftBoxTab());

        //Listener
        Bukkit.getPluginManager().registerEvents(new GiftBoxListener(), plugin);

        //Config
        config = new Config("config");
        config.setPlugin(plugin);
        config.loadDefaultPluginConfig();
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}

