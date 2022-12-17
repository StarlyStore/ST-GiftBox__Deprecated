package net.starly.giftbox.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class GiftBoxTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) return sender.isOp() ? List.of("보기", "보내기", "모두지급", "리로드") : List.of("보기");
        else if (args.length == 2 && !"리로드".equals(args[1])) return args[0].equals("보내기") ? (sender.hasPermission("starly.giftbox.send") ? null : Collections.emptyList())
                : (sender.hasPermission("starly.giftbox.view.other") ? null : Collections.emptyList());
        else return Collections.emptyList();
    }
}
