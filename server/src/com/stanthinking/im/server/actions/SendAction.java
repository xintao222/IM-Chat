package com.stanthinking.im.server.actions;

import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;
import com.stanthinking.im.server.Dispatcher;
import static com.stanthinking.im.server.actions.helpers.AuthorizationHelpers.*;

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
