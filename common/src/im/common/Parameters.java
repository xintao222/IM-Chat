package im.common;

import java.util.HashMap;

/**
 *
 * @author Stanislav Peshterliev
 */
public class Parameters extends HashMap<String, String> {

    private static final long serialVersionUID = 1L;

    public static Parameters of(String... args) {
        Parameters parameters = new Parameters();
        boolean odd = true;
        String key = "";
        for (String arg : args) {
            if (odd) {
                key = arg;
                odd = false;
            } else {
                parameters.put(key, arg);
                odd = true;
            }
        }

        return parameters;
    }
}
