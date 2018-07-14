package me.borawski.perseus.utils.packet;

import me.borawski.perseus.Perseus;
import me.borawski.perseus.utils.log.LogLevel;
import org.jboss.netty.buffer.ChannelBuffer;

/**
 * @author ethan
 */
public class DefaultPacketHandler implements PacketHandler {

    /*
     * Methods
     */

    public void handle(Packet packet, ChannelBuffer channelBuffer) {
        Perseus.getLogger().log(LogLevel.WARN, "Unhandled packet: " + packet.getId());
    }
}
