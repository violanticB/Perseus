package me.borawski.perseus.utils.player;

/**
 * @author ethan
 */
public enum Rank {

    GUEST("Guest"),
    VIP("VIP"),
    MODERATOR("Moderator"),
    ADMINISTRATOR("Administrator"),
    DEVELOPER("Developer"),
    STAFFMANAGER("Manager"),
    OWNER("Owner");

    private String name;

    Rank(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
