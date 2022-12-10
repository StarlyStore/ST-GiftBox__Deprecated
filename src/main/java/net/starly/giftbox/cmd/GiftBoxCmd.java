package net.starly.giftbox.cmd;

import net.starly.giftbox.data.GiftBoxData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiftBoxCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                GiftBoxData giftBoxData = new GiftBoxData(player);
                giftBoxData.open(player);
            } else if (args.length > 1) {
                switch (args[0]) {
                    case "보내기" -> {
                        if (args.length > 2) {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                            GiftBoxData giftBoxData = new GiftBoxData(target);

                        } else {
                            for (OfflinePlayer target : Bukkit.getOfflinePlayers()) {
                                GiftBoxData giftBoxData = new GiftBoxData(target);
                                giftBoxData.open(player);
                            }
                        }
                    }
                    case "보기" -> {

                    }

                }
            }
        }


        return false;
    }
}
