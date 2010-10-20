package com.stanthinking.im.server.actions;

import com.google.common.collect.Sets;
import com.stanthinking.im.util.Callback;
import com.stanthinking.im.common.Sender;
import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;
import com.stanthinking.im.common.Parameters;
import com.stanthinking.im.common.User;
import com.stanthinking.im.server.Dispatcher;
import com.stanthinking.im.server.model.UserModel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.stanthinking.im.server.actions.helpers.DatabaseHelpers.*;
import com.stanthinking.im.util.Validator;

/**
 *
 * @author Stanislav Peshterliev
 */
public class RegisterAction implements Action {

    @Override
    public void execute(Message message, Client client, Dispatcher dispatcher) {
        final Sender sender = client.getSender();

        try {
            String username = message.sender;
            String email = message.parameters.get("email");
            String password = message.parameters.get("password");

            if (!validatePresenceOf(username, sender, "Username required")
                    || !validateUniquenessOf(username, sender)
                    || !validateEmail(email, sender)
                    || !validatePresenceOf(password, sender, "Password required")) {
                return;
            }


            User user = new User(
                    username,
                    email,
                    User.cryptPassword(password));

            UserModel.create(user);

            sender.send(new Message("server", "RegistrationSuccessful"));
        } catch (SQLException e) {
            sendSQLExceptionError(sender);
            Logger.getLogger(RegisterAction.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    static boolean validateUniquenessOf(String username, Sender sender) throws SQLException {
        User user = UserModel.findByUsername(username);

        if (user == null && !Sets.newHashSet("all", "server").contains(username)) {
            return true;
        }

        sender.send(new Message("server", "RegistrationFailed", Parameters.of("reason", "User with this username already exists")));
        return false;
    }

    boolean validatePresenceOf(String username, final Sender sender, final String message) {
        return Validator.validatePresenceOf(username, new Callback<Void>() {

            @Override
            public void call(Void arg) {
                sender.send(new Message("server", "RegistrationFailed", Parameters.of("reason", message)));
            }
        });
    }

    boolean validateEmail(String email, final Sender sender) {
        return Validator.validateEmail(email, new Callback<Void>() {

            @Override
            public void call(Void arg) {
                sender.send(new Message("server", "RegistrationFailed", Parameters.of("reason", "Not valid email")));
            }
        });
    }
}
