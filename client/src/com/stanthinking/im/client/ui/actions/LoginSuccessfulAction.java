package com.stanthinking.im.client.ui.actions;

import com.google.common.base.Splitter;
import com.stanthinking.im.client.Frames;
import com.stanthinking.im.client.ui.LoginFrame;
import com.stanthinking.im.client.ui.MainFrame;
import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;
import com.stanthinking.im.common.User;

/**
 *
 * @author Stanislav Peshterliev
 */
public class LoginSuccessfulAction implements Action {

    @Override
    public void execute(Message message, Client client) {
        Frames.get(LoginFrame.class).setVisible(false);
        User user = new User(
                message.parameters.get("username"),
                message.parameters.get("email"),
                message.parameters.get("cryptedPassword")
            );
        client.setUser(user);
        
        client.setAuthtoken(message.authtoken);

        MainFrame mainFrame = Frames.get(MainFrame.class);

        Iterable<String> usernames = Splitter.on(",")
                .omitEmptyStrings()
                .trimResults()
                .split(message.parameters.get("users"));

        for (String username : usernames) {
            if (username.equals(user.username)) continue;
            mainFrame.addUser(username);
        }

        mainFrame.setUsername(user.username);
        mainFrame.setVisible(true);
    }
}
