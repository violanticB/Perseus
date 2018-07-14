package me.borawski.perseus.net.codec;

import me.borawski.perseus.Perseus;
import me.borawski.perseus.utils.packet.Packet;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * @author ethan
 */
public class Decoder extends FrameDecoder {

    /*
     * Methods
     */

    protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer) throws Exception {
        Packet packet = new Packet(channelBuffer.readUnsignedByte(), channel);
        Perseus.getPacketManager().handle(packet, channelBuffer);
        return packet;
    }
}
