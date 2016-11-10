package me.timlampen.leaderboard.listeners;

import me.timlampen.leaderboard.GUICreator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

/**
 * Created by Primary on 10/28/2016.
 */
public class InteractListener implements Listener {
    GUICreator c;
    public InteractListener(GUICreator c){
        this.c = c;
    }
    @EventHandler
    public void onInteract(InventoryInteractEvent event){
        if(event.getInventory()!=null && event.getInventory().getName().equalsIgnoreCase("Leaderboard")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(InventoryClickEvent event){
        if(event.getClickedInventory()!=null && event.getClickedInventory().getName().equalsIgnoreCase("Leaderboard")){
            event.setCancelled(true);
            if(event.getCurrentItem()!=null){
                if((event.getCurrentItem().getType()== Material.APPLE) || (event.getCurrentItem().getType()== Material.GOLDEN_APPLE)){
                    c.show((Player)event.getWhoClicked(), parse(event.getCurrentItem().getItemMeta().getDisplayName()));
                }
            }
        }
    }

    public int parse(String s ){
        s = ChatColor.stripColor(s);
        return Integer.parseInt(s.split(": ")[1]);
    }
}
