package me.borawski.perseus.utils.packet;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * @author ethan
 */
public interface PacketHandler {

    public void handle(Packet packet, ChannelBuffer channelBuffer);
}
