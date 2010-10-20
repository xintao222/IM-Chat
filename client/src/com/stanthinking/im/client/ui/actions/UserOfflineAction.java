package com.stanthinking.im.client.ui.actions;

import com.stanthinking.im.client.ui.ChatFrame;
import com.stanthinking.im.client.Frames;
import com.stanthinking.im.client.ui.MainFrame;
import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;

/**
 *
 * @author Stanislav Peshterliev
 */
public class UserOfflineAction implements Action {

    @Override
    public void execute(Message message, Client client) {
        String username = message.parameters.get("user");
        Frames.get(MainFrame.class).removeUser(username);

        if (Frames.exists(username)) {
            Frames.get(ChatFrame.class, username).append("*User went offline*");
        }
    }

}
