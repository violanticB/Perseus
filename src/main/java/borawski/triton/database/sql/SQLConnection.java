package borawski.triton.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Ethan on 5/10/2018.
 */
public class SQLConnection {

    private Connection connection;
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    /*
     * Methods
     */

    public SQLConnection(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        openConnection();
    }

    private void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Getters
     */

    public Connection getConnection() {
        if (connection == null) {
            openConnection();
            return connection;
        }

        try {
            if (connection.isClosed()) {
                openConnection();
                return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public SQLConnection setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public String getHost() {
        return host;
    }

    public SQLConnection setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public SQLConnection setPort(int port) {
        this.port = port;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public SQLConnection setDatabase(String database) {
        this.database = database;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SQLConnection setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SQLConnection setPassword(String password) {
        this.password = password;
        return this;
    }
}
