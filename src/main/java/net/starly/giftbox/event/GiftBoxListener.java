package net.starly.giftbox.event;

import net.starly.giftbox.data.GiftBoxData;
import net.starly.giftbox.gui.GiftBoxGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GiftBoxListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (GiftBoxData.giftBoxGUIHashMap.containsKey(p.getUniqueId())) {
            e.setCancelled(true);

            int slot = e.getSlot();
            GiftBoxGUI giftBoxGUI = GiftBoxData.giftBoxGUIHashMap.get(p.getUniqueId());

            if (slot == giftBoxGUI.NEXT_PAGE_SLOT) {
                giftBoxGUI.nextPage(p);
            } else if (slot == giftBoxGUI.PREVIOUS_PAGE_SLOT) {
                giftBoxGUI.previousPage(p);
            } else if (slot == 49) {
                giftBoxGUI.select(p);
            }
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent e) {
        List<Inventory> inventories = new ArrayList<>();
        GiftBoxData.giftBoxGUIHashMap.keySet().forEach(uuid -> inventories.add(Bukkit.getPlayer(uuid).getOpenInventory().getTopInventory()));

        if (inventories.contains(e.getDestination())) e.setCancelled(true);
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e) {
        if (GiftBoxData.giftBoxGUIHashMap.containsKey(e.getPlayer().getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        GiftBoxData.giftBoxGUIHashMap.remove(p.getUniqueId());
    }
}
