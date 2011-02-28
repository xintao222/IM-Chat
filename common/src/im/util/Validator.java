package im.util;

/**
 *
 * @author Stanislav Peshterliev
 */
public class Validator {
    public static boolean validateEmail(String email, Callback<Void> callback) {
        if (email != null && email.matches("\\w+@\\w+\\.[A-za-z]{2,3}")) return true;

        if (callback != null) callback.call(null);
        
        return false;
    }

    public static boolean validateEmail(String email) {
        return validateEmail(email, null);
    }

    public static boolean validatePresenceOf(String text, Callback<Void> callback) {
        if (text != null && !text.isEmpty()) return true;
        
        if (callback != null) callback.call(null);

        return false;
    }

    public static boolean validatePresenceOf(String text) {
        return validatePresenceOf(text, null);
    }
}
