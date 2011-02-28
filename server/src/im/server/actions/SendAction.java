package im.server.actions;

import im.common.Client;
import im.common.Message;
import im.server.Dispatcher;
import static im.server.actions.helpers.AuthorizationHelpers.*;

/**
 *
 * @author Stanislav Peshterliev
 */
public class SendAction implements Action {
    @Override
    public void execute(Message message, Client client, Dispatcher dispatcher) {
        if (!autorizedMessage(message, client)) {
            sendAuthorizationError(client.getSender());

            return;
        }
        
        dispatcher.dispatch(new Message(message.sender, "Received", "", message.receivers, message.parameters, message.date));
    }
}
