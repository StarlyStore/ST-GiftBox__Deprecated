package net.starly.giftbox.data;

import net.starly.giftbox.GiftBoxMain;
import net.starly.giftbox.util.StringData;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import skyexcel.data.file.Config;

import java.util.ArrayList;
import java.util.List;

public class PlayerGiftBoxData {

    private final OfflinePlayer offlinePlayer;

    private final Config config;

    private final String path = "items";

    private final List<ItemStack> itemStacks;

    public PlayerGiftBoxData(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
        this.config = new Config("GiftBoxData/" + offlinePlayer.getUniqueId());
        this.config.setPlugin(GiftBoxMain.getPlugin());
        this.itemStacks = getItemStacks();
    }


    public void removeItem() {
        if (!itemStacks.isEmpty()) {
            itemStacks.remove(0);
            config.getConfig().set(path, itemStacks);
            config.saveConfig();
        }
    }

    public void addItem(Player player, ItemStack itemStack) {
        List<ItemStack> items = getItemStacks();
        if (!items.isEmpty()) {
            items.add(itemStack);
        } else {
            items = new ArrayList<>();
            items.add(itemStack);
        }

        config.getConfig().set(path, items);
        config.saveConfig();
        player.sendMessage(StringData.prefix() + StringData.sendItem(offlinePlayer));
        if (offlinePlayer.isOnline()) {
            offlinePlayer.getPlayer().sendMessage(StringData.prefix() + StringData.getItem());
        }

    }

    /***
     *
     * @return items의 경로에 있는 아이템을 가져온다. 만약 콘피그가 없다면, 빈 리스트를 반환한다.
     */

    public List<ItemStack> getItemStacks() {
        if (config != null)
            return (List<ItemStack>) (config.getConfig().get(path) == null ? List.of() : config.getConfig().get(path));
        return new ArrayList<>();
    }
}
