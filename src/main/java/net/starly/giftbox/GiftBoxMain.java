package net.starly.giftbox;

import net.starly.core.bstats.Metrics;
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
        // DEPENDENCY
        if (Bukkit.getPluginManager().getPlugin("ST-Core") == null) {
            Bukkit.getLogger().warning("[" + plugin.getName() + "] ST-Core 플러그인이 적용되지 않았습니다! 플러그인을 비활성화합니다.");
            Bukkit.getLogger().warning("[" + plugin.getName() + "] 다운로드 링크 : &fhttps://discord.gg/TF8jqSJjCG");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        plugin = this;

        new Metrics(this, 17175);


        // CONFIG
        config = new Config("config", plugin);
        config.loadDefaultPluginConfig();


        // COMMAND
        Bukkit.getPluginCommand("선물함").setExecutor(new GiftBoxCmd());
        Bukkit.getPluginCommand("선물함").setTabCompleter(new GiftBoxTabComplete());


        // EVENT
        Bukkit.getPluginManager().registerEvents(new GiftBoxListener(), plugin);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}

