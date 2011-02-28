package im.server.actions.helpers;

import im.common.Message;
import im.common.Parameters;
import im.common.Sender;

final public class DatabaseHelpers {
    public static void sendSQLExceptionError(Sender sender) {
        sender.send(new Message("server", "LoginFailed", Parameters.of("reason", "Database error")));
    }
}
