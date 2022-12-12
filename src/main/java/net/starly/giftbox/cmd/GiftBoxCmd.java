package net.starly.giftbox.cmd;

import net.starly.giftbox.GiftBoxMain;
import net.starly.giftbox.data.GiftBoxData;
import net.starly.giftbox.data.PlayerGiftBoxData;
import net.starly.giftbox.gui.GiftBoxGUI;
import net.starly.giftbox.util.MessageUtil;
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
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return false;

        if (args.length == 0) {
            GiftBoxGUI giftBox = new GiftBoxGUI(p);
            giftBox.update(p);
            GiftBoxData.giftBoxGUIHashMap.put(p.getUniqueId(), giftBox);

            return true;
        }

        if ("보내기".equals(args[0])) {
            if (!p.hasPermission("starly.giftbox.send")) {
                p.sendMessage(MessageUtil.getNoPermission());
                return false;
            }

            if (args.length != 2) {
                p.sendMessage(MessageUtil.getWrongCommand());
                return false;
            }

            ItemStack itemStack = p.getInventory().getItemInMainHand();
            if (itemStack.getType().equals(Material.AIR)) {
                p.sendMessage(MessageUtil.getNoItemInHand());
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                p.sendMessage(MessageUtil.getNoPlayer());
                return false;
            }

            new PlayerGiftBoxData(target).addItem(p, itemStack);
            return true;
        } else if ("보기".equals(args[0])) {
            if (args.length == 1) {
                if (!p.hasPermission("starly.giftbox.view.self")) {
                    p.sendMessage(MessageUtil.getNoPermission());
                    return false;
                }

                GiftBoxGUI giftBox = new GiftBoxGUI(p);
                giftBox.update(p);
                GiftBoxData.giftBoxGUIHashMap.put(p.getUniqueId(), giftBox);

                return true;
            }
            if (args.length == 2) {
                if (!p.hasPermission("starly.giftbox.view.other")) {
                    p.sendMessage(MessageUtil.getNoPermission());
                    return false;
                }

                GiftBoxGUI giftBox = new GiftBoxGUI(Bukkit.getPlayer(args[1]));
                giftBox.update(p);
                GiftBoxData.giftBoxGUIHashMap.put(p.getUniqueId(), giftBox);

                return true;
            }

            p.sendMessage(MessageUtil.getWrongCommand());
            return false;
        } else if ("리로드".equals(args[0])) {
            if (!p.hasPermission("starly.giftbox.reload")) {
                p.sendMessage(MessageUtil.getNoPermission());
                return false;
            }

            GiftBoxMain.config.reloadConfig();
            p.sendMessage("§a설정 파일을 리로드했습니다.");
            return true;
        }

        p.sendMessage(MessageUtil.getWrongCommand());
        return false;
    }
}
