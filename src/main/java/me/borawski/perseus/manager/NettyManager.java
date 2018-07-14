package me.borawski.perseus.manager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.borawski.perseus.Perseus;
import me.borawski.perseus.net.ServerHandler;

/**
 * @author ethan
 */
public class NettyManager {

    private ServerBootstrap bootstrap;
    private int port;

    /*
     * Methods
     */
    public NettyManager(int port) {
        this.port = port;
    }

    public void run()  {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        this.bootstrap = new ServerBootstrap()
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerHandler());
        try {
            this.bootstrap.bind(port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Perseus.getLogger().log(me.borawski.perseus.utils.log.LogLevel.INFO, "Netty server binded to port: " + port);
    }

    /*
     * Getters
     */

    public int getPort() {
        return this.port;
    }
}
