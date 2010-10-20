package com.stanthinking.im.common;

import java.util.Map;
import com.google.common.collect.Maps;

/**
 *
 * @author Stanislav Peshterliev
 */
public class Controller<T> {

    private Map<String, T> actions;
    private String namespace;

    public Controller(String namespace) {
        actions = Maps.newHashMap();
        this.namespace = namespace;
    }

    public T getAction(String name) throws ClassNotFoundException {
        if (!actions.containsKey(name)) {
            @SuppressWarnings("unchecked")
            Class<T> actionClass = (Class<T>) Class.forName(namespace + "." + name);
            T action;

            try {
                action = actionClass.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            actions.put(name, action);

            return action;
        } else {
            return actions.get(name);
        }
    }
}
