package im.server.actions;

import im.common.Client;
import im.common.Message;
import im.server.Dispatcher;

/**
 *
 * @author Stanislav Peshterliev
 */
public class KeepAliveAction implements Action {

    @Override
    public void execute(Message message, Client client, Dispatcher dispatcher) {
        // do noting
    }

}
