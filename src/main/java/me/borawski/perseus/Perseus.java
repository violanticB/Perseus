package me.borawski.perseus;

import me.borawski.perseus.commands.CommandListener;
import me.borawski.perseus.manager.*;
import me.borawski.perseus.utils.log.LogLevel;
import me.borawski.perseus.utils.log.Logger;

import java.io.File;

/**
 * @author ethan
 */
public class Perseus {

    private static File config;
    private static Logger logger;
    private static InstanceManager instanceManager;
    private static NettyManager nettyManager;
    private static PacketManager packetManager;
    private static RedisManager redisManager;
    private static SQLManager sqlManager;
    private static ProxyManager proxyManager;
    public static int online = 0;

    public static void main(String[] args) {
        enablePerseus();
    }

    private static void enablePerseus() {
        config = new File("config.yml");
        logger = new Logger(new File("log.txt"));

        sqlManager = new SQLManager("localhost", 3306, "mcc", "root", "1024311");
        instanceManager = new InstanceManager();

        try {
            nettyManager = new NettyManager(8000);
        } catch (Exception e) {
            getLogger().log(LogLevel.ERROR, "Port already in use.");
        }

        nettyManager.run();

        packetManager = new PacketManager(255);

        try {
            redisManager = new RedisManager("localhost", 0000);
        } catch (Exception e) {
            getLogger().log(LogLevel.ERROR, "Connection to Redis failed.");
        }

        proxyManager = new ProxyManager();

        getLogger().log(LogLevel.INFO, "Perseus has been enabled.");

        new CommandListener();
    }

    /*
     * Getters
     */

    public static File getConfig() {
        return config;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static InstanceManager getInstanceManager() {
        return instanceManager;
    }

    public static PacketManager getPacketManager() {
        return packetManager;
    }

    public static SQLManager getSqlManager() {
        return sqlManager;
    }

    public static ProxyManager getProxyManager() {
        return proxyManager;
    }

    /*
     * Setters
     */
}
