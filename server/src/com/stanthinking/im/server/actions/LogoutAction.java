package com.stanthinking.im.server.actions;

import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;
import com.stanthinking.im.server.Dispatcher;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.stanthinking.im.server.actions.helpers.AuthorizationHelpers.*;

/**
 *
 * @author Stanislav Peshterliev
 */
public class LogoutAction implements Action {

    @Override
    public void execute(Message message, Client client, Dispatcher dispatcher) {
        if (!autorizedMessage(message, client)) {
            sendAuthorizationError(client.getSender());

            return;
        }
        
        try {
            client.getSocket().close();
        } catch (IOException e) {
            Logger.getLogger(LogoutAction.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
