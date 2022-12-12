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
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (GiftBoxData.giftBoxGUIHashMap.containsKey(p.getUniqueId())) {
            int slot = e.getSlot();
            GiftBoxGUI giftBoxGUI = GiftBoxData.giftBoxGUIHashMap.get(p.getUniqueId());

            if (slot == giftBoxGUI.NEXT_PAGE_SLOT) {
                giftBoxGUI.nextPage(p);
            } else if (slot == giftBoxGUI.PREVIOUS_PAGE_SLOT) {
                giftBoxGUI.previousPage(p);
            } else if (slot == 49) {
                giftBoxGUI.select(p);
            }

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        GiftBoxData.giftBoxGUIHashMap.remove(p.getUniqueId());
    }
}
