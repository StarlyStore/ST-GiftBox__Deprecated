package net.starly.giftbox.data;

import net.starly.giftbox.GiftBoxMain;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import skyexcel.data.file.Config;

public class GiftBoxData {

    private OfflinePlayer offlinePlayer;

    private Config config;

    public GiftBoxData(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
        this.config = new Config("GiftBoxData/" + offlinePlayer.getUniqueId());
        this.config.setPlugin(GiftBoxMain.plugin);
    }

    public void open(Player player){

    }
}
