package net.starly.giftbox.data;

import net.starly.giftbox.GiftBoxMain;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import skyexcel.data.file.Config;

import java.util.List;

public class GiftBoxPlayerData {

    private OfflinePlayer offlinePlayer;

    private Config config;

    private final String path = "items";

    private List<ItemStack> itemStacks;

    public GiftBoxPlayerData(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
        this.config = new Config("GiftBoxData/" + offlinePlayer.getUniqueId());
        this.config.setPlugin(GiftBoxMain.plugin);
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
        itemStacks.add(itemStack);
        config.getConfig().set(path, itemStacks);
        config.saveConfig();
        player.sendMessage("架 §6" + this.offlinePlayer.getName() + "§f님에게 성공적으로 §6아이템§f을 §a보냈습니다!");

    }

    /***
     *
     * @return items의 경로에 있는 아이템을 가져온다. 만약 콘피그가 없다면, 빈 리스트를 반환한다.
     */

    public List<ItemStack> getItemStacks() {
        return (List<ItemStack>) (config.getConfig().get(path));
    }
}
