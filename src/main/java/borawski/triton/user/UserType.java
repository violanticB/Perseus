package borawski.triton.user;

/**
 * Created by Ethan on 5/10/2018.
 */
public enum UserType {

    ADMIN("Administrator"),
    DEV("Developer"),
    CUSTOMER_SUPPORT("Customer Support"),
    USER("User"),
    GUEST("Guest");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
