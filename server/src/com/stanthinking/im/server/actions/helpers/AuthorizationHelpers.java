package com.stanthinking.im.server.actions.helpers;

import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;
import com.stanthinking.im.common.Sender;
import com.stanthinking.im.util.Hashs;
import java.util.Date;

/**
 *
 * @author Stanislav Peshterliev
 */
final public class AuthorizationHelpers {
    public static boolean autorizedMessage(Message message, Client client) {
        return message.authtoken.equals(client.getAuthtoken());
    }

    public static void sendAuthorizationError(Sender sender) {
        sender.send(new Message("server", "AuthorizationFailed"));
    }

    public static String generateAuthtoken(Client client) {
        String ip = client.getSocket().getInetAddress().toString();
        
        return Hashs.sh1(
            ip + (new Date()).getTime() + client.getUser().cryptedPassword
        );
    }
}