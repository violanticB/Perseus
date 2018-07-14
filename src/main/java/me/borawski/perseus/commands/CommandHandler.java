package me.borawski.perseus.commands;

import me.borawski.perseus.Perseus;
import me.borawski.perseus.instance.Instance;
import me.borawski.perseus.utils.instance.InstanceUtil;
import me.borawski.perseus.utils.log.LogLevel;
import me.borawski.perseus.utils.math.UtilMath;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * @author ethan
 */
public class CommandHandler {

    /*
     * Methods
     */
    public static void runCommand(String[] command) throws UnknownHostException {
        if (command[0].equalsIgnoreCase("create")) {
            if (command.length < 2) {
                Perseus.getLogger().log(LogLevel.INFO, "Usage: create <server type> (amount)");
                return;
            }

            if (Perseus.getInstanceManager().getServerType(command[1]) == null) {
                Perseus.getLogger().log(LogLevel.INFO, "Invalid server type!");
                return;
            }

            if (command.length == 2) {
                Perseus.getInstanceManager().startInstance(Perseus.getInstanceManager().getServerType(command[1]));
                return;
            }

            if (!UtilMath.isInteger(command[2])) {
                Perseus.getLogger().log(LogLevel.INFO, "Invalid amount of instances!");
                return;
            }

            Perseus.getLogger().log(LogLevel.INFO, "Created " + command[2] + " " + Perseus.getInstanceManager().getServerType(command[1]).getName() + " instances!");

            for (int i = 0; i < Integer.parseInt(command[2]); i++) {
                Perseus.getInstanceManager().startInstance(Perseus.getInstanceManager().getServerType(command[1]));
            }

            return;
        }

        if (command[0].equalsIgnoreCase("destroy")) {
                try {
                    Perseus.getInstanceManager().stopInstance(command[1]);
                    Perseus.getLogger().log(LogLevel.INFO, "The instance '" + command[1] + "' was stopped and destroyed");
                } catch (Exception e) {
                    e.printStackTrace();
                    Perseus.getLogger().log(LogLevel.ERROR, "The instance '" + command[1] + "' does not exist!");
                }
        }

        if (command[0].equalsIgnoreCase("list")) {
            if (Perseus.getInstanceManager().getInstances().isEmpty()) {
                Perseus.getLogger().log(LogLevel.INFO, "There are no (registered) instances!");
                return;
            }

            if (command.length == 1) {
                Perseus.getLogger().log(LogLevel.INFO, "---- Registered Instances ----");
                for (Instance instance : Perseus.getInstanceManager().getInstances()) {
                    Perseus.getLogger().log(LogLevel.INFO, instance.getName() + " : " + InetAddress.getLocalHost().getHostAddress() + ":" + instance.getPort() + " : " + instance.getPlayers() + "/" + instance.getServerType().getPlayers());
                }
                return;
            }
        }

        if (command[0].equalsIgnoreCase("refresh")) {
            try {
                Perseus.getInstanceManager().refreshServerTypes();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Perseus.getLogger().log(LogLevel.INFO, "Refreshed server types!");
            return;
        }

        if (command[0].equalsIgnoreCase("servercache")) {
            System.out.println(InstanceUtil.getServers());
        }
    }
}
