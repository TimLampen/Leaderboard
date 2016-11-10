package me.timlampen.leaderboard.listeners;

import me.timlampen.leaderboard.LPlayer;
import me.timlampen.leaderboard.Leaderboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Primary on 10/26/2016.
 */
public class KillListener implements Listener {
    Leaderboard p;
    HashMap<UUID, List<Cooldown>> cds = new HashMap<UUID, List<Cooldown>>();
    public KillListener(Leaderboard p){
        this.p = p;
    }

    @EventHandler
    public void onKill(final PlayerDeathEvent event) {
        final Player player = event.getEntity().getKiller();
        final Player def = event.getEntity();
        if (player != null && !def.getAddress().getAddress().equals(player.getAddress().getAddress()) && (!p.cd.containsKey(player.getUniqueId()) || (p.cd.containsKey(player.getUniqueId()) && !p.cd.get(player.getUniqueId()).contains(def.getUniqueId())))) {
            LPlayer lPlayer = p.getLPlayer(player);
            lPlayer.setKills(lPlayer.getKills() + 1);
            lPlayer.setItem(p.generateItem(lPlayer));
            LPlayer ldef = p.getLPlayer(def);
            ldef.setDeaths(ldef.getDeaths()+1);
            ldef.setItem(p.generateItem(ldef));
        }
    }

    public boolean checkCD(UUID player, UUID target){
        if(cds.containsKey(player)){
            List<Cooldown> cd = cds.get(player);
            boolean found = false;
            for(Cooldown tcd : cd){
                if(tcd.player.equals(target)){
                    if(System.currentTimeMillis()>=tcd.time){
                        cd.remove(tcd);
                        cds.put(player, cd);
                        found = true;
                    }
                    break;
                }
            }
            return found;
        }
        else{
            cds.put(player, Arrays.asList(new Cooldown[]{new Cooldown(target, System.currentTimeMillis()+(20*60*10))}));
            return true;
        }
    }
}
