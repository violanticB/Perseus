package me.borawski.perseus.manager;

import redis.clients.jedis.Jedis;

/**
 * @author ethan
 */
public class RedisManager {

    private Jedis jedis;
    private String host;
    private int port;

    /*
     * Methods
     */

    public RedisManager(String host, int port) {
        this.host = host;
        this.port = port;

        this.jedis = new Jedis(host, port);
    }

    /*
     * Getters
     */

    public Jedis getJedis() {
        return this.jedis;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }
}
