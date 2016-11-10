package me.timlampen.leaderboard;

import me.timlampen.leaderboard.listeners.InteractListener;
import me.timlampen.leaderboard.listeners.KillListener;
import net.brcdev.gangs.GangsPlusAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Primary on 10/26/2016.
 */
public class Leaderboard extends JavaPlugin{
    public HashMap<UUID, ArrayList<UUID>> cd = new HashMap<UUID, ArrayList<UUID>>();
    HashMap<UUID, LPlayer> stats = new HashMap<UUID, LPlayer>();
    @Override
    public void onEnable(){
        GUICreator c = new GUICreator(this);
       // Bukkit.getPluginManager().registerEvents(new JoinListener(this ,c), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(c), this);
        Bukkit.getPluginManager().registerEvents(new KillListener(this), this);
        for(String sUUID : getConfig().getConfigurationSection("").getKeys(false)){
            UUID uuid = UUID.fromString(sUUID);
            int kills = getConfig().getInt(uuid + ".kills");
            int deaths = getConfig().getInt(uuid + ".deaths");
            String name = getConfig().getString(uuid + ".name");
            LPlayer player = new LPlayer(name, null, kills, deaths);
            player.setItem(generateItem(player));
            stats.put(uuid, player);
        }
        //test();
        getCommand("lb").setExecutor(new CommandHandler(c));
    }
    String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    public void test(){
        for(int i = 0; i<10; i++){
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int z = 0; z < 14; z++) {
                sb.append(candidateChars.charAt(random.nextInt(candidateChars
                        .length())));
            }
            LPlayer player = new LPlayer(sb.toString(), null, random.nextInt(100), random.nextInt(100));
            player.setItem(generateItem(player));
            stats.put(UUID.randomUUID(), player);
        }
    }

    @Override
    public void onDisable(){
        for(UUID uuid : stats.keySet()){
            LPlayer player = stats.get(uuid);
            getConfig().set(uuid + ".kills", player.getKills());
            getConfig().set(uuid + ".deaths", player.getDeaths());
            getConfig().set(uuid + ".name", player.getName());
        }
        saveConfig();
    }

    public LPlayer getLPlayer(Player player){
        if(stats.containsKey(player.getUniqueId())){
            return stats.get(player.getUniqueId());
        }
        else{
            String gang = GangsPlusAPI.isInGang(player) ? GangsPlusAPI.getPlayersGang(player).getName() : "";
            ArrayList<String> lore = new ArrayList<String>();
            ItemStack is = new ItemStack(Material.SKULL_ITEM);
            SkullMeta im = (SkullMeta) is.getItemMeta();
            im.setDisplayName(ChatColor.GOLD + player.getName());
            lore.add(ChatColor.YELLOW + "Kills: " + ChatColor.GRAY + "0");
            lore.add(ChatColor.YELLOW + "Deaths: " + ChatColor.GRAY + "0");
            lore.add(ChatColor.YELLOW + "KDR: " + ChatColor.GRAY + "0");
            lore.add(ChatColor.YELLOW + "Gang: " + ChatColor.GRAY + gang);
            //lore.add(ChatColor.YELLOW + "Bounty: " + ChatColor.GRAY + "0");
            im.setLore(lore);
            im.setOwner(player.getName());
            is.setItemMeta(im);
            LPlayer lplayer = new LPlayer(player.getName(), is,0, 0);
            stats.put(player.getUniqueId(), lplayer);
            return lplayer;
        }
    }

    public ItemStack generateItem(LPlayer lPlayer){
        ArrayList<String> lore = new ArrayList<String>();
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
        SkullMeta im = (SkullMeta) is.getItemMeta();
        im.setDisplayName(ChatColor.GOLD + lPlayer.getName());
        DecimalFormat df = new DecimalFormat("#.##");
        double kills = lPlayer.getKills();
        double deaths = lPlayer.getDeaths();
        double kda;
        if(lPlayer.getDeaths()>0){
            kda = kills/deaths;
        }
        else{
            kda = kills;
        }
        String KDA = df.format(kda).toString();
        lore.add(ChatColor.YELLOW + "Kills: " + ChatColor.GRAY + kills);
        lore.add(ChatColor.YELLOW + "Deaths: " + ChatColor.GRAY + deaths);
        lore.add(ChatColor.YELLOW + "KDR: " + ChatColor.GRAY + KDA);
        im.setLore(lore);
        im.setOwner(lPlayer.getName());
        is.setItemMeta(im);
        return  is;
    }
}
