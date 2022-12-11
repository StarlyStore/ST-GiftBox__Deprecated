package net.starly.giftbox.util;

import net.starly.giftbox.GiftBoxMain;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public class StringData {


    public static String prefix() {
        return translate(GiftBoxMain.config.getString("prefix"));
    }

    public static String reward() {
        return translate(GiftBoxMain.config.getString("messages.reward"));
    }

    public static String inventoryFull() {
        return translate(GiftBoxMain.config.getString("messages.inventoryFull"));
    }

    public static String noPermission() {
        return translate(GiftBoxMain.config.getString("messages.noPermission"));
    }

    public static String noItem() {
        return translate(GiftBoxMain.config.getString("messages.noItem"));
    }

    public static String sendItem(OfflinePlayer target) {
        return translate(GiftBoxMain.config.getString("messages.sendItem")).replaceAll("%target%", target.getName());
    }

    public static String getItem() {
        return translate(GiftBoxMain.config.getString("messages.getItem"));
    }

    public static String noHandItem() {
        return translate(GiftBoxMain.config.getString("messages.noHandItem"));
    }

    private static String translate(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
