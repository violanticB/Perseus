package me.borawski.perseus.manager;

import io.netty.channel.ChannelId;
import me.borawski.perseus.Perseus;
import me.borawski.perseus.instance.Instance;
import me.borawski.perseus.instance.ServerType;
import me.borawski.perseus.utils.log.LogLevel;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author ethan
 */
public class InstanceManager {

    private Set<Instance> instances;
    private Set<ServerType> serverTypes;
    private HashMap<String, Integer> ids;
    private HashMap<String, Integer> ports;
    private static HashMap<ChannelId, String> assignments;

    /*
     * Methods
     */

    public InstanceManager() {
        this.instances = new HashSet<>();
        this.serverTypes = new HashSet<>();
        this.ids = new HashMap<>();
        this.ports = new HashMap<>();
        assignments = new HashMap<>();

        try {
            refreshServerTypes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startInstance(ServerType serverType) {
        Instance instance = new Instance(serverType);
        instance.start();
        getInstances().add(instance);

        Perseus.getProxyManager().getProxyChannels().forEach((s, channel) -> {

        });
    }

    public void stopInstance(String server) {
        Instance instance = getInstance(server);
        instance.stop();
        getInstances().remove(instance);
    }

    public void refreshServerTypes() throws SQLException {
        PreparedStatement preparedStatement = Perseus.getSqlManager().getConnection().prepareStatement("SELECT * FROM servertypes");
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet == null) {
            Perseus.getLogger().log(LogLevel.INFO, "No server types found!");
            return;
        }

        while(resultSet.next()) {
            getServerTypes().add(new ServerType(resultSet.getString("name"), resultSet.getInt("port"), new File(resultSet.getString("plugins")), resultSet.getInt("players"), resultSet.getInt("memory")));
            Perseus.getLogger().log(LogLevel.INFO, "Registered server type " + resultSet.getString("name"));
        }

        preparedStatement.close();
        resultSet.close();

        setup();
    }

    private void setup() {
        ports.clear();
        ids.clear();
        for (ServerType serverType : getServerTypes()) {
            ports.put(serverType.getName(), serverType.getPort());
            ids.put(serverType.getName(), 0);
        }
    }

    /*
     * Getters
     */

    public String getAssignmentID(final int port) {
        final String[] assignment = new String[1];
        getServerTypes().stream().filter(new Predicate<ServerType>() {
            @Override
            public boolean test(ServerType type) {
                return port > type.getPort() && port < (type.getPort() + 1000);
            }
        }).forEach(new Consumer<ServerType>() {
            @Override
            public void accept(ServerType type) {
                String assignments = "" + type.getName() + "-" + Math.abs(type.getPort() - port);
                assignment[0] = assignments;
            }
        });

        return assignment[0];
    }

    public Set<Instance> getInstances() {
        return this.instances;
    }

    public Instance getInstance(final String name) {
        final Instance[] i = {null};
        getInstances().forEach(new Consumer<Instance>() {
            @Override
            public void accept(Instance instance) {
                if(instance.getName().equalsIgnoreCase(name)) {
                    i[0] = instance;
                }
            }
        });

        return i[0];
    }

    public Set<ServerType> getServerTypes() {
        return this.serverTypes;
    }

    public ServerType getServerType(String type) {
        for (ServerType serverType : getServerTypes()) {
            if (serverType.getName().equalsIgnoreCase(type)) return serverType;
        }

        return null;
    }

    public String getNextID(ServerType serverType) {
        int id = ids.get(serverType.getName()) + 1;
        ids.put(serverType.getName(), id);

        return serverType.getName() + "-" + id;
    }

    public int getNextPort(ServerType serverType) {
        int port = ports.get(serverType.getName()) + 1;
        ports.put(serverType.getName(), port);

        return port;
    }

    /*
     * Setters
     */
}
