package im.client.ui.actions;

import im.client.Frames;
import im.client.ui.RegistrationFrame;
import im.common.Client;
import im.common.Message;

/**
 *
 * @author Stanislav Peshterliev
 */
public class RegistrationFailedAction implements Action {

    @Override
    public void execute(Message message, Client client) {
        Frames.get(RegistrationFrame.class).setError(
                message.parameters.get("reason")
        );
    }
}
