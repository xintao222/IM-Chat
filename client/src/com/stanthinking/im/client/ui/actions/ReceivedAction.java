package com.stanthinking.im.client.ui.actions;

import com.stanthinking.im.client.ui.ChatFrame;
import com.stanthinking.im.client.Frames;
import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;

/**
 *
 * @author Stanislav Peshterliev
 */
public class ReceivedAction implements Action {

    @Override
    public void execute(Message message, Client client) {
        ChatFrame chatFrame = Frames.get(ChatFrame.class, message.sender);
        chatFrame.setUsername(message.sender);

        chatFrame.append(message);

        if (!chatFrame.isVisible()) {
            chatFrame.setVisible(true);
        }
    }

}
