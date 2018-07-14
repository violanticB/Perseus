package me.borawski.perseus.manager;

import me.borawski.perseus.utils.packet.Packet;
import me.borawski.perseus.utils.packet.PacketHandler;
import org.jboss.netty.buffer.ChannelBuffer;

/**
 * @author ethan
 */
public class PacketManager {

    private final PacketHandler[] mass;

    /*
     * Methods
     */

    public PacketManager(int maxPackets) {
        this.mass = new PacketHandler[maxPackets];
    }

    public void handle(Packet packet, ChannelBuffer channelBuffer) {
        if (mass[packet.getId()] != null) {
            mass[packet.getId()].handle(packet, channelBuffer);
            return;
        }
        mass[0].handle(packet, channelBuffer);
    }
}
