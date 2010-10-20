package com.stanthinking.im.client.ui.actions;

import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;

/**
 *
 * @author Stanislav Peshterliev
 */
public interface Action {
    public void execute(Message message, Client client);
}
