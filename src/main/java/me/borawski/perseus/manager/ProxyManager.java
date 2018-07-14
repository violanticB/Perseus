package me.borawski.perseus.manager;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ethan on 8/3/2017.
 */
public class ProxyManager {

    private Map<String, Channel> proxyChannels;

    public ProxyManager() {
        proxyChannels = new ConcurrentHashMap<>();
    }

    public Map<String, Channel> getProxyChannels() {
        return proxyChannels;
    }

    public void registerProxy(String name, Channel channel) {
        getProxyChannels().put(name, channel);
    }

    public void sendServerRegistration(String name, InetSocketAddress address) {
        getProxyChannels().forEach((s, channel) -> {
            String add = "ADD_SERVER_INFO:"
                    + name + ":" + address.getAddress().getHostAddress() + ";" + address.getPort();
            sendMessage(channel, add);
        });
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
