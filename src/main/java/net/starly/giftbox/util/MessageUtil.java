package net.starly.giftbox.util;

import net.starly.giftbox.GiftBoxMain;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtil {
    private static String getPrefix() {
        return GiftBoxMain.config.getString("prefix");
    }


    public static String getReceivedItem() {
        return color(GiftBoxMain.config.getString("messages.received_item"));
    }

    public static String getInventoryIsFull() {
        return color(GiftBoxMain.config.getString("messages.inventory_is_full"));
    }

    public static String getNoPermission() {
        return color(GiftBoxMain.config.getString("messages.no_permission"));
    }

    public static String getNoItemInGiftBox() {
        return color(GiftBoxMain.config.getString("messages.no_item_in_giftbox"));
    }

    public static String getItemSent(Player target) {
        return color(GiftBoxMain.config.getString("messages.item_sent"))
                .replace("{target}", target.getDisplayName());
    }

    public static String getHasItemInGiftBox() {
        return color(GiftBoxMain.config.getString("messages.has_item_in_giftbox"));
    }

    public static String getNoItemInHand() {
        return color(GiftBoxMain.config.getString("messages.no_item_in_hand"));
    }

    public static String getWrongCommand() {
        return color(GiftBoxMain.config.getString("messages.wrong_command"));
    }

    public static String getNoPlayer() {
        return color(GiftBoxMain.config.getString("messages.no_player"));
    }

    private static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', getPrefix() + msg);
    }
}
