package com.stanthinking.im.client.ui.actions;

import com.stanthinking.im.client.Frames;
import com.stanthinking.im.client.ui.LoginFrame;
import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;

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
