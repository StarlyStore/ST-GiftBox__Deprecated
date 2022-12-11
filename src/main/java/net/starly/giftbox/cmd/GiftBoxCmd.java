package net.starly.giftbox.cmd;

import net.starly.giftbox.data.GiftBoxData;
import net.starly.giftbox.data.GiftBoxPlayerData;
import net.starly.giftbox.gui.GiftBoxGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GiftBoxCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                if ("보내기".equalsIgnoreCase(args[0])) {

                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (!itemStack.getType().equals(Material.AIR)) {
                        if (args.length > 1) {

                            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                            GiftBoxPlayerData giftBox = new GiftBoxPlayerData(target);

                            giftBox.addItem(player, itemStack);
                        }
                    }
                } else if ("보기".equalsIgnoreCase(args[0])) {
                    if (args.length > 1) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        GiftBoxGUI giftBox = new GiftBoxGUI(target);
                        giftBox.update(player);
                        GiftBoxData.giftBoxGUIHashMap.put(player.getUniqueId(), giftBox);
                    }
                }
            } else {
                GiftBoxGUI giftBox = new GiftBoxGUI(player);
                giftBox.update(player);
                GiftBoxData.giftBoxGUIHashMap.put(player.getUniqueId(), giftBox);
            }
        }
        return false;
    }
}
