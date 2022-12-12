package net.starly.giftbox.gui;

import net.starly.giftbox.GiftBoxMain;
import net.starly.giftbox.data.PlayerGiftBoxData;
import net.starly.giftbox.util.InventoryUpdate;
import net.starly.giftbox.util.Items;
import net.starly.giftbox.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GiftBoxGUI {
    private Inventory inv;

    private int currentPage = 1;
    private int maxPage;

    private final String title = "선물함";
    public final int NEXT_PAGE_SLOT = 50;
    public final int PREVIOUS_PAGE_SLOT = 48;
    private final List<Integer> slots = List.of(0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 47, 48, 50, 51, 52, 53);

    private final PlayerGiftBoxData playerGiftBoxData;
    private final List<ItemStack> materials;

    public GiftBoxGUI(OfflinePlayer offlinePlayer) {
        playerGiftBoxData = new PlayerGiftBoxData(offlinePlayer);
        materials = playerGiftBoxData.getItemStacks();
    }


    public void update(Player player) {
        int divide = 36;

        this.maxPage = materials.size() / divide;
        if (inv == null) {
            maxPage += 1;
            inv = Bukkit.createInventory(null, 54, title + " (" + currentPage + "/" + maxPage + ")");
        }

        if (currentPage != maxPage) next();

        int slot = 9;
        defaultGUI();

        for (ItemStack items : materials) {
            if (slot == 45) break;

            inv.setItem(slot, items);
            slot++;
        }

        player.openInventory(inv);
    }


    public void select(Player player) {
        if (currentPage == 1) {
            if (!materials.isEmpty()) {
                if (isInventoryFull(player)) {
                    player.sendMessage(MessageUtil.getInventoryIsFull());
                } else {
                    int divide = 36;


                    player.getInventory().addItem(materials.get(0));
                    playerGiftBoxData.removeItem();
                    player.sendMessage(MessageUtil.getReceivedItem());

                    inv.clear();
                    defaultGUI();
                    this.maxPage = materials.size() / divide;

                    if (inv == null) {
                        maxPage += 1;
                        inv = Bukkit.createInventory(null, 54, title + " (" + currentPage + "/" + maxPage + ")");
                    }
                    int i = 0;

                    for (ItemStack itemStack : materials) {
                        i++;

                        if (currentPage != 1 && i <= (currentPage - 1) * 36) continue;
                        if (i > currentPage * 36) break;

                        inv.addItem(itemStack);
                    }
                }
            } else {
                player.sendMessage(MessageUtil.getNoItemInGiftBox());
            }

            if (materials.isEmpty()) player.closeInventory();
        }
    }


    public void nextPage(Player player) {
        if (maxPage != currentPage) {
            InventoryUpdate.updateInventory(GiftBoxMain.getPlugin(), player, title + " (" + currentPage + "/" + maxPage + ")");
            inv.clear();
            defaultGUI();

            int i = 0;
            for (ItemStack itemStack : materials) {
                i++;

                if (currentPage != 1 && i <= (currentPage - 1) * 36) continue;
                if (i > currentPage * 36) break;

                inv.addItem(itemStack);
            }
        }
    }

    public void previousPage(Player player) {
        if (currentPage != 1) {
            InventoryUpdate.updateInventory(GiftBoxMain.getPlugin(), player, title + " (" + --currentPage + "/" + maxPage + ")");
            inv.clear();
            defaultGUI();

            int i = 0;
            for (ItemStack itemStack : materials) {
                i++;

                if (currentPage != 1 && i <= (currentPage - 1) * 36) continue;
                if (i > currentPage * 36) break;

                inv.addItem(itemStack);
            }
        }
    }

    private void next() {
        Items.newItem("§a다음 페이지", Material.OAK_SIGN, 1, List.of(), NEXT_PAGE_SLOT, inv);
    }

    private void previous() {
        Items.newItem("§7이전 페이지", Material.OAK_SIGN, 1, List.of(), PREVIOUS_PAGE_SLOT, inv);
    }

    private void clearItem(int slot) {
        inv.setItem(slot, null);
    }


    private void defaultGUI() {

        Items.newItem("§r§6선물함", Material.CHEST, 1, List.of("§r§7받은 선물 수 : " + materials.size()), 4, inv);
        Items.newItem("§r§b수령", Material.END_CRYSTAL, 1, List.of("§r§7이곳을 우클릭하여 아이템을 수령하세요!"), 49, inv);

        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = glass.getItemMeta(); meta.setDisplayName("§r"); glass.setItemMeta(meta);
        slots.forEach(i -> inv.setItem(i, glass));

        if (currentPage > 1) previous();
        if (currentPage != maxPage) next();

    }

    private boolean isInventoryFull(Player p) {
        int slot = p.getInventory().firstEmpty();

        return slot == -1;
    }
}
