package im.server.actions;

import com.google.common.base.Joiner;
import java.sql.SQLException;

import im.common.Message;
import im.common.Client;
import im.common.Parameters;
import im.common.Sender;
import im.common.User;
import im.server.Dispatcher;
import im.server.model.UserModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import static im.server.actions.helpers.DatabaseHelpers.*;
import static im.server.actions.helpers.AuthorizationHelpers.generateAuthtoken;

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
