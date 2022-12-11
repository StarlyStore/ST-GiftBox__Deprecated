package net.starly.giftbox.event;

import net.starly.giftbox.data.GiftBoxData;
import net.starly.giftbox.gui.GiftBoxGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GiftBoxListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (GiftBoxData.giftBoxGUIHashMap.containsKey(player.getUniqueId())) {
                int slot = event.getSlot();
                GiftBoxGUI giftBoxGUI = GiftBoxData.giftBoxGUIHashMap.get(player.getUniqueId());

                if (slot == giftBoxGUI.getNEXT_PAGE_SLOT()) { // 다음 페이지 클릭
                    giftBoxGUI.nextPage(player);
                } else if (slot == giftBoxGUI.getPREVIOUS_PAGE_SLOT()) { // 다음 페이지 클릭
                    giftBoxGUI.previousPage(player);
                } else if (slot == 49) {
                    giftBoxGUI.select(player);
                }

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (GiftBoxData.giftBoxGUIHashMap.containsKey(player.getUniqueId())) {
                GiftBoxData.giftBoxGUIHashMap.remove(player.getUniqueId());
            }
        }
    }
}
