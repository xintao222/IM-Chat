package im.client.ui.actions;

import im.client.ui.ChatFrame;
import im.client.Frames;
import im.client.ui.MainFrame;
import im.common.Client;
import im.common.Message;

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
