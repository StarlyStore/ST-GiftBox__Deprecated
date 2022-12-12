package net.starly.giftbox.gui;

import net.starly.giftbox.GiftBoxMain;
import net.starly.giftbox.data.PlayerGiftBoxData;
import net.starly.giftbox.util.InventoryUpdate;
import net.starly.giftbox.util.Items;
import net.starly.giftbox.util.StringData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiftBoxGUI {
    private Inventory inv;

    private int currentPage = 1;

    private int totalPage;

    public final int NEXT_PAGE_SLOT = 50;

    public final int PREVIOUS_PAGE_SLOT = 48;

    private final String title = "선물함";


    private OfflinePlayer offlinePlayer;


    private final PlayerGiftBoxData playerGiftBoxData;

    private final List<ItemStack> materials;


    private List<Integer> slots = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 47, 48, 49, 50, 51, 52, 53, 53);

    public GiftBoxGUI(OfflinePlayer offlinePlayer) {
        playerGiftBoxData = new PlayerGiftBoxData(offlinePlayer);
        materials = playerGiftBoxData.getItemStacks();
    }


    public void update(Player player) {

        int divide = 36;


        this.totalPage = materials.size() / divide;
        if (inv == null) {
            totalPage += 1;
            inv = Bukkit.createInventory(null, 54, title + " (" + currentPage + "/" + totalPage + ")");
        }

        if (currentPage != totalPage)
            next(); // 다음 버튼 추가

        int slot = 9;

        defaultGUI();


        for (ItemStack items : materials) {
            if (slot == 45) {
                break;
            }
            inv.setItem(slot, items);
            slot++;

        }


        player.openInventory(inv);
    }


    public void select(Player player) {

        if (currentPage == 1) {
            if (!materials.isEmpty()) {
                if (isInventoryFull(player)) {
                    player.sendMessage(StringData.prefix() + StringData.inventoryFull());
                } else {
                    int divide = 36;


                    player.getInventory().addItem(materials.get(0));
                    playerGiftBoxData.removeItem();
                    player.sendMessage(StringData.prefix() + StringData.reward());

                    inv.clear();
                    defaultGUI();
                    this.totalPage = materials.size() / divide;

                    if (inv == null) {
                        totalPage += 1;
                        inv = Bukkit.createInventory(null, 54, title + " (" + currentPage + "/" + totalPage + ")");
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
                player.sendMessage(StringData.prefix() + StringData.noItem());
            }
        }
    }


    public void nextPage(Player player) {

        if (totalPage != currentPage) {
            InventoryUpdate.updateInventory(GiftBoxMain.getPlugin(), player, title + " (" + ++currentPage + "/" + totalPage + ")");
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
            InventoryUpdate.updateInventory(GiftBoxMain.getPlugin(), player, title + " (" + --currentPage + "/" + totalPage + ")");
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
        Items.newItem("§a다음 페이지", Material.OAK_SIGN, 1, Arrays.asList(), NEXT_PAGE_SLOT, inv);
    }

    private void previous() {
        Items.newItem("§7이전 페이지", Material.OAK_SIGN, 1, Arrays.asList(), PREVIOUS_PAGE_SLOT, inv);
    }

    private void clearItem(int slot) {
        ItemStack item = inv.getItem(slot);
        item.setAmount(0);
        inv.setItem(slot, item);
    }


    private void defaultGUI() {

        Items.newItem("선물함", Material.CHEST, 1, List.of("현제 : " + materials.size()), 4, inv);
        Items.newItem("모두 수령", Material.END_CRYSTAL, 1, List.of("§7아이템을 우클릭하여 아이템을 수령하세요!"), 49, inv);


        List.of(0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 47, 51, 52, 53).forEach(i -> {
            inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
        });

        if (currentPage > 1) {
            previous();
        }
        if (currentPage != totalPage)
            next(); // 다음 버튼 추가

    }


    public void setInv(Inventory inv) {
        this.inv = inv;
    }


    /**
     * @return 인벤토리에 아이템이 존재하는지 확인.
     */
    public List<ItemStack> getItems() {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 9; i < 36; i++) {
            if (inv.getItem(i) != null) {
                itemStacks.add(inv.getItem(i));
            }
        }
        return itemStacks;
    }

    private boolean isInventoryFull(Player p) {
        int slot = p.getInventory().firstEmpty();

        return slot == -1;
    }
}
