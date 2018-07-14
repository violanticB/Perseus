package me.borawski.perseus.utils.log;

/**
 * @author ethan
 */
public enum LogLevel {

    INFO("[INFO]"),
    WARN("[WARNING]"),
    ERROR("[ERROR] Error"),
    DEBUG("[DEBUG]");

    private String prefix;

    /*
     * Methods
     */

    LogLevel(String prefix) {
        this.prefix = prefix;
    }

    /*
     * Getters
     */

    public String getPrefix() {
        return this.prefix;
    }
}
