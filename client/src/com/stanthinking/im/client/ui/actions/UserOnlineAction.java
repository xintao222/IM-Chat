package com.stanthinking.im.client.ui.actions;

import com.stanthinking.im.client.Frames;
import com.stanthinking.im.client.ui.MainFrame;
import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;

/**
 *
 * @author Stanislav Peshterliev
 */
public class UserOnlineAction implements Action {

    @Override
    public void execute(Message message, Client client) {
        String username = message.parameters.get("user");
        
        if (username.equals(client.getUser().username)) return;
        
        Frames.get(MainFrame.class).addUser(username);
    }

}
