package com.stanthinking.im.client;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author Stanislav Peshterliev
 */
final public class Frames {
    private static Map<String, JFrame> frames = Maps.newHashMap();
    
    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> classObject, String name) {
        if (!frames.containsKey(name)) {
            try {
                frames.put(name, (JFrame) classObject.newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return (T) frames.get(name);
    }

    public static <T> T get(Class<T> classObject) {
        return get(classObject, classObject.getCanonicalName());
    }

    public static boolean exists(String name) {
        return frames.containsKey(name);
    }
}
