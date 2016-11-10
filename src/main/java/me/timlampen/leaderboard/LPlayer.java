package me.timlampen.leaderboard;

import net.brcdev.gangs.gang.Gang;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Primary on 10/26/2016.
 */
public class LPlayer {
    String name;
    int kills;
    int deaths;
    ItemStack item;

    public LPlayer(String name, ItemStack is, int kills, int deaths){
        this.deaths = deaths;
        this.item = is;
        this.name = name;
        this.kills = kills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
