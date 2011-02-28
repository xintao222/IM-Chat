package im.client.ui.actions;

import im.client.Frames;
import im.client.ui.MainFrame;
import im.common.Client;
import im.common.Message;

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
