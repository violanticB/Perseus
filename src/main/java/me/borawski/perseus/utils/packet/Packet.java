package me.borawski.perseus.utils.packet;

import org.jboss.netty.channel.Channel;

/**
 * @author ethan
 */
public class Packet {

    private final int id;
    private final Channel channel;

    /*
     * Methods
     */

    public Packet(int id, Channel channel) {
        this.id = id;
        this.channel = channel;
    }

    /*
     * Getters
     */

    public int getId() {
        return this.id;
    }

    public Channel getChannel() {
        return this.channel;
    }
}
