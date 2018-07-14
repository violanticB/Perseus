package me.borawski.perseus.utils.log;

import java.io.File;

/**
 * @author ethan
 */
public class Logger {

    private File logFile;

    /*
     * Methods
     */

    public Logger(File logFile) {
        if (logFile == null) {
            logFile = new File("log.txt");
            return;
        }

        this.logFile = logFile;
    }

    public void log(LogLevel logLevel, String message) {
        System.out.println("[Perseus] " + logLevel.getPrefix() + " : " + message);
    }

    /*
     * Getters
     */

    public File getLogFile() {
        return this.logFile;
    }

    /*
     * Setters
     */
}
