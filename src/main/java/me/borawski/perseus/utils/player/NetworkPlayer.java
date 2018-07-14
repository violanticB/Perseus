package me.borawski.perseus.utils.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author ethan
 */
public class NetworkPlayer {

    private static List<NetworkPlayer> cache = new ArrayList<NetworkPlayer>();

    private UUID uuid;
    private Rank rank;

    /*
     * Methods
     */

    public NetworkPlayer(UUID uuid) {
        this.uuid = uuid;

        cache.add(this);
    }

    /*
     * Getters
     */

    public static NetworkPlayer getNetworkPlayer(UUID uuid) {
        for (NetworkPlayer player : cache) {
            if (player.getUuid().equals(uuid)) return player;
        }

        return null;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Rank getRank() {
        return this.rank;
    }

    /*
     * Setters
     */
}
