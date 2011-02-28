package im.client.ui.actions;

import im.client.Frames;
import im.client.ui.LoginFrame;
import im.common.Client;
import im.common.Message;

/**
 *
 * @author Stanislav Peshterliev
 */
public class LoginFailedAction implements Action {

    @Override
    public void execute(Message message, Client client) {
        Frames.get(LoginFrame.class).setError(
                message.parameters.get("reason")
        );
    }
}
