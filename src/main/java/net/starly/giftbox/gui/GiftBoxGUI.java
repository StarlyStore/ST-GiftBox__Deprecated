package net.starly.giftbox.gui;

import net.starly.giftbox.GiftBoxMain;
import net.starly.giftbox.data.GiftBoxPlayerData;
import net.starly.giftbox.util.InventoryUpdate;
import net.starly.giftbox.util.Items;
import net.starly.giftbox.util.StringData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    private final int NEXT_PAGE_SLOT = 50;

    private final int PREVIOUS_PAGE_SLOT = 48;

    private final String title = "선물함";


    private OfflinePlayer offlinePlayer;


    private GiftBoxPlayerData giftBoxPlayerData;

    private final List<ItemStack> materials;


    private List<Integer> slots = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 47, 48, 49, 50, 51, 52, 53, 53);

    public GiftBoxGUI(OfflinePlayer offlinePlayer) {
        giftBoxPlayerData = new GiftBoxPlayerData(offlinePlayer);
        materials = giftBoxPlayerData.getItemStacks();
    }


    public void update(Player player) {

        int divide = 36;


        this.totalPage = materials.size() / divide;
        if (inv == null) {
            totalPage += 1;
            inv = Bukkit.createInventory(null, 54, title + " (" + currentPage + "/" + totalPage + ")");
        }


        List.of(0, 1, 2, 3, 5, 6, 7, 8).forEach(i -> {
            inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
        });

        Items.newItem("선물함", Material.CHEST, 1, List.of("현제 : " + materials.size()), 4, inv);
        if (currentPage != totalPage)
            next(); // 다음 버튼 추가

        int slot = 9;
        Items.newItem("모두 수령", Material.END_CRYSTAL, 1, List.of("§7아이템을 우클릭하여 아이템을 수령하세요!"), 49, inv);

        /**
         * i = 현제 페이지에서 1을 제거 후, 44를 곱한다.(처음 페이지를 인식 하기 위함. (0일경우))
         * 이후 i가 현제 페이지와 44를 곱한 값보다 작거나 같을때 i값을 계속 더해준다. (materialList의 값을 불러오기 위함.),
         */


        for (ItemStack items : materials) {
            if (slot == 45) {
                break;
            }
            inv.setItem(slot, items);
            slot++;

        }
        setInv(inv);
        player.openInventory(inv);
    }


    public void select(Player player) {
//        player.sendMessage(ChatColor.GREEN + "아이템을 성공적으로 수령하였습니다.");

        if (!materials.isEmpty()) {
            if (isInventoryFull(player)) {
                player.sendMessage("§c인벤토리가 가득 찼습니다.");
            } else {
                int divide = 36;


                player.getInventory().addItem(materials.get(0));
                giftBoxPlayerData.removeItem();
                player.sendMessage(StringData.reward());
                int slot = 9;
                inv.clear();
                this.totalPage = materials.size() / divide;
                if (currentPage != totalPage)
                    next(); // 다음 버튼 추가

                if (inv == null) {
                    totalPage += 1;
                    inv = Bukkit.createInventory(null, 54, title + " (" + currentPage + "/" + totalPage + ")");
                }


                List.of(0, 1, 2, 3, 5, 6, 7, 8).forEach(i -> {
                    inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
                });

                Items.newItem("선물함", Material.CHEST, 1, List.of("현제 : " + materials.size()), 4, inv);

                for (ItemStack items : materials) {
                    if (slot == 45) {
                        break;
                    }
                    inv.setItem(slot, items);
                    slot++;

                }


                Items.newItem("모두 수령", Material.END_CRYSTAL, 1, List.of("§7아이템을 우클릭하여 아이템을 수령하세요!"), 49, inv);

            }
        } else {
            player.sendMessage(StringData.noItem());
        }
    }


    public void nextPage(Player player) {

        currentPage += 1;

        update(player);
    }

    public void previousPage(Player player, boolean isShift) {

        if (!isShift) {
            InventoryUpdate.updateInventory(GiftBoxMain.getPlugin(), player, title + " (" + --currentPage + "/" + totalPage + ")");
            update(player);

            if (currentPage != 1) {
                previous(); //이전 버튼 추가
            } else {
                clearItem(PREVIOUS_PAGE_SLOT);
            }
        } else {
            currentPage = 1;
            InventoryUpdate.updateInventory(GiftBoxMain.getPlugin(), player, title + " (" + currentPage + "/" + totalPage + ")");
            update(player);

            if (currentPage != 1) {
                previous(); //이전 버튼 추가
            } else {
                clearItem(PREVIOUS_PAGE_SLOT);
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


    public int getPREVIOUS_PAGE_SLOT() {
        return PREVIOUS_PAGE_SLOT;
    }

    public int getNEXT_PAGE_SLOT() {
        return NEXT_PAGE_SLOT;
    }


    public void setInv(Inventory inv) {
        this.inv = inv;
    }


    private boolean isInventoryFull(Player p) {
        int slot = p.getInventory().firstEmpty();

        return slot == -1;
    }
}
