package me.timlampen.leaderboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Created by Primary on 10/26/2016.
 */
public class GUICreator {
    Leaderboard p;

    public GUICreator(Leaderboard p){
        this.p = p;
    }
    PlayerComparator pc = new PlayerComparator();
    public void show(Player player, int page){
        if(page<=0){
            return;
        }
        Inventory inv = Bukkit.createInventory(player, 54, "Leaderboard");
        ArrayList<LPlayer> lplayers = new ArrayList<LPlayer>();
        lplayers.addAll(p.stats.values());
        Collections.sort(lplayers, pc);
        Collections.reverse(lplayers);
        int index = (page*54)-54 - (page==1 ? 0 : 1);

        ItemStack forward = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta im = forward.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "To page: " + (page+1));
        forward.setItemMeta(im);

        ItemStack back = new ItemStack(Material.APPLE);
        im = back.getItemMeta();
        im.setDisplayName(ChatColor.RED + "To page: " + (page-1));
        back.setItemMeta(im);

        inv.setItem(45, back);
        inv.setItem(53, forward);
        for(int i = 0; i<52; i++) {
            //System.out.println(i + " | " + index);
            if(i==45){
                continue;
            }
            else {
                if (lplayers.size() > index) {
                    inv.setItem(i, lplayers.get(index).getItem());
                } else {
                    break;
                }
                index++;
            }
        }
        player.openInventory(inv);
    }
}
