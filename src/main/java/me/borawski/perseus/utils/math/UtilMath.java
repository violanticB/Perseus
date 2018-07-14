package me.borawski.perseus.utils.math;

/**
 * @author ethan
 */
public class UtilMath {

    public static boolean isInteger(String parse) {
        try {
            Integer.parseInt(parse);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
