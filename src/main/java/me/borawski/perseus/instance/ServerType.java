package me.borawski.perseus.instance;

import java.io.File;

/**
 * @author ethan
 */
public class ServerType {

    private String name;
    private int port;
    private File template;
    private int players;
    private int memory;

    /*
     * Methods
     */

    public ServerType(String name, int port, File template, int players, int memory) {
        this.name = name;
        this.port = port;
        this.template = template;
        this.players = players;
        this.memory = memory;
    }

    /*
     * Getters
     */

    public String getName() {
        return this.name;
    }

    public int getPort() {
        return this.port;
    }

    public File getTemplate() {
        return template;
    }

    public int getPlayers() {
        return this.players;
    }

    public int getMaxRam() {
        return this.memory;
    }

    public int getMinRam() {
        return this.memory / 2;
    }
}
