package me.borawski.perseus.instance;

import me.borawski.perseus.Perseus;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author ethan
 */
public class Instance {

    private Process process;
    private ServerType serverType;
    private String name;
    private String status;
    private int players;
    private int port;

    /*
     * Methods
     */

    public Instance(ServerType serverType) {
        this.serverType = serverType;
    }

    public void start() {
        this.name = Perseus.getInstanceManager().getNextID(serverType);
        this.port = Perseus.getInstanceManager().getNextPort(serverType);
        File file = new File("C:\\Users\\Ethan\\Desktop\\m\\instances\\" + name);
        if(!file.exists()) {
            file.mkdirs();
        }

        File template = getServerType().getTemplate();
        try {
            FileUtils.copyDirectory(template, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProcessBuilder processBuilder = new ProcessBuilder("java", "-server", "-Dcom.mojang.eula.agree=true", "-jar",
                "-Xmx" + getServerType().getMaxRam() + "m",
                "-Xms" + getServerType().getMinRam() + "m",
                "C:\\Users\\Ethan\\Desktop\\m\\global\\spigot.jar",
                "--port", "" + port,
                "--bukkit-settings", "C:\\Users\\Ethan\\Desktop\\m\\global\\bukkit.yml",
                "--spigot-settings", "C:\\Users\\Ethan\\Desktop\\m\\global\\spigot.yml",
                "--world", "world");
        processBuilder.directory(file);
        try {
            process = processBuilder.start();
            status = "ONLINE";
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.players = 0;
    }

    public void stop() {
        getProcess().destroy();
    }

    /*
     * Getters
     */
    public Process getProcess() {
        return this.process;
    }

    public ServerType getServerType() {
        return this.serverType;
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPort() {
        return this.port;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

}
