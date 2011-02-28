package im.client.ui.actions;

import im.common.Client;
import im.common.Message;

/**
 *
 * @author Stanislav Peshterliev
 */
public interface Action {
    public void execute(Message message, Client client);
}
