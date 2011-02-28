package im.client.ui.actions;

import im.client.ui.ChatFrame;
import im.client.Frames;
import im.common.Client;
import im.common.Message;

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
