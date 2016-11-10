package me.timlampen.leaderboard;

import java.util.Comparator;

/**
 * Created by Primary on 10/28/2016.
 */
public class PlayerComparator implements Comparator<LPlayer> {

    public int compare(LPlayer p1, LPlayer p2) {
        return p1.kills - p2.kills;
    }
}
