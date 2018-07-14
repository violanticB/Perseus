package me.borawski.perseus.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.*;
import me.borawski.perseus.Perseus;
import me.borawski.perseus.utils.instance.InstanceUtil;
import me.borawski.perseus.utils.log.LogLevel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * @author ethan
 */
public class ChannelHandler extends SimpleChannelInboundHandler<String> {

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static Map<String, Channel> instanceChannels = new ConcurrentHashMap<String, Channel>();

    /*
     * Methods
     */

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String arg1) throws Exception {
        Channel incoming = arg0.channel();
        System.out.println(arg0);
        if(arg1.contains("PLAYER_LOGIN")) {
            Perseus.online++;
            Perseus.getLogger().log(LogLevel.INFO, "Total players on network: " + Perseus.online);
            return;
        } else if(arg1.contains("PLAYER_LOGOUT")) {
            Perseus.online--;
            Perseus.getLogger().log(LogLevel.INFO, "Total players on network: " + Perseus.online);
            return;
        } else if(arg1.contains("REQUEST_INSTANCE_ID")) {
            Integer port = Integer.parseInt(arg1.split(":")[1]);
            String assignment = "label_assignment:" + (Perseus.getInstanceManager().getAssignmentID(port));
            sendMessage(arg0.channel(), assignment);
            instanceChannels.forEach(new BiConsumer<String, Channel>() {
                @Override
                public void accept(String s, Channel channel) {
                    if(s.equalsIgnoreCase(String.valueOf(port))) {
                        channels.remove(channel);
                    }
                }
            });

            instanceChannels.put(port + "", arg0.channel());
            Perseus.getProxyManager().sendServerRegistration(Perseus.getInstanceManager().getAssignmentID(port), new InetSocketAddress("localhost", port));
            return;
        } else if(arg1.contains("REQUEST_NETWORK_INSTANCES")) {
            String message = "network_instances_refresh@" + InstanceUtil.getServers();
            sendMessage(arg0.channel(), message);
            return;
        } else if(arg1.contains("SET_INSTANCE_STATUS")) {
            String[] message = arg1.split(":");
            String instance = message[2];
            String status = message[3];
            Perseus.getInstanceManager().getInstance(instance).setStatus(status);
            return;
        } else if(arg1.contains("STAFF_CHAT")) {
            String[] message = arg1.split(":");
            String user = message[1];
            String msg = message[2];

            channels.forEach((channel -> {
                sendMessage(channel, "staff_chat_receive:" + user + ":" + msg);
            }));

            return;
        } else if(arg1.contains("SET_INSTANCE_PLAYERS")) {
            String[] message = arg1.split(":");
            Integer players = Integer.valueOf(message[2]);
            String instance = message[1];
            Perseus.getInstanceManager().getInstance(instance).setPlayers(players);
        }

        // PROXY MESSAGING //

        else if(arg1.contains("PROXY_INITIALIZED")) {
            String[] message = arg1.split(":");
            String proxy = message[1];
            Perseus.getProxyManager().registerProxy(proxy, arg0.channel());
        }

        Perseus.getLogger().log(LogLevel.INFO, "Incoming message ('" + arg1 + "') from " + incoming.remoteAddress());
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception{
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.
        final ChannelFuture cf = ctx.channel().writeAndFlush("Welcome to the network").sync();
        cf.addListener(new FutureListener<Void>() {
            public void operationComplete(Future<Void> future) {
                if (!cf.isSuccess()) {
                    System.out.println("[Perseus] [NET] : Could not send message to client");
                }
            }
        });
        ctx.channel().flush();
        if(!cf.isSuccess()) {
            Perseus.getLogger().log(LogLevel.ERROR, "Sending message to client failed");
        }

        if(channels.contains(ctx.channel())) {
            return;
        }

        Perseus.getLogger().log(LogLevel.INFO, "Instance client connected (" + ctx.channel().remoteAddress() + ")");
        Perseus.getLogger().log(LogLevel.INFO, "Total instances on network: " + channels.size());

        channels.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        channels.remove(ctx.channel());
        Perseus.getLogger().log(LogLevel.INFO, "Instance client disconnected (" + ctx.channel().remoteAddress() + ")");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //ctx.close();
    }

    public void sendMessage(Channel channel, String s) {
        ChannelFuture lastWriteFuture = null;
        String line = null;
        try {
            line = s;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sends the received line to the server.
        lastWriteFuture = channel.writeAndFlush(line + "\r\n");

        // If user typed the 'bye' command, wait until the server closes
        // the connection.

        // Wait until all messages are flushed before closing the channel.
        if (lastWriteFuture != null) {
            try {
                lastWriteFuture.sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
