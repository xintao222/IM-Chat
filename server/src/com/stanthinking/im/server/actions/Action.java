package com.stanthinking.im.server.actions;

import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;
import com.stanthinking.im.server.Dispatcher;

/**
 *
 * @author Stanislav Peshterliev
 */
public interface Action {
    void execute(Message message, Client client, Dispatcher dispatcher);
}
