package net.starly.giftbox.util;

import net.starly.giftbox.GiftBoxMain;

public class StringData {
    public static String reward() {
        return GiftBoxMain.config.getString("messages.reward");
    }

    public static String inventoryFull() {
        return GiftBoxMain.config.getString("messages.inventory-full");
    }

    public static String noPermission() {
        return GiftBoxMain.config.getString("messages.no-permission");
    }

    public static String noItem() {
        return GiftBoxMain.config.getString("messages.no-item");
    }
}
