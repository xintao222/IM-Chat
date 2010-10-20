package com.stanthinking.im.server.actions;

import com.google.common.base.Joiner;
import java.sql.SQLException;

import com.stanthinking.im.common.Message;
import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Parameters;
import com.stanthinking.im.common.Sender;
import com.stanthinking.im.common.User;
import com.stanthinking.im.server.Dispatcher;
import com.stanthinking.im.server.model.UserModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.stanthinking.im.server.actions.helpers.DatabaseHelpers.*;
import static com.stanthinking.im.server.actions.helpers.AuthorizationHelpers.generateAuthtoken;

/**
 *
 * @author Stanislav Peshterliev
 */
public class LoginAction implements Action {

    @Override
    public void execute(Message message, Client client, Dispatcher dispatcher) {
        Sender sender = client.getSender();
        if (dispatcher.getClient(message.sender) != null) {
            sender.send(new Message("server", "LoginFailed", Parameters.of("reason", "Already logged")));

            return;
        }
        try {
            String cryptedPassword = User.cryptPassword(message.parameters.get("password"));
            User user = UserModel.findByUsername(message.sender);

            if (user != null && user.cryptedPassword.equals(cryptedPassword)) {
                client.setUser(user);
                String authtoken = generateAuthtoken(client);

                String users = Joiner.on(",").join(dispatcher.getClientsNames());

                client.setAuthtoken(authtoken);

                sender.send(new Message("server", "LoginSuccessful",
                        authtoken, null,
                        Parameters.of("users", users,
                                      "username", user.username,
                                      "email", user.email,
                                      "password", user.cryptedPassword)));

                dispatcher.addClient(user.username, client);
            } else {
                sender.send(new Message("server", "LoginFailed", Parameters.of("reason", "Invalid credentials")));
            }
        } catch (SQLException e) {
            sendSQLExceptionError(sender);
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
