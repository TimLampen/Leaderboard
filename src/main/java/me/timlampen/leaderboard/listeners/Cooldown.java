package me.timlampen.leaderboard.listeners;

import java.util.UUID;

/**
 * Created by Primary on 11/5/2016.
 */
public class Cooldown {
    UUID player;
    long time;
    public Cooldown(UUID player, long time){
        this.player = player;
        this.time = time;
    }
}
