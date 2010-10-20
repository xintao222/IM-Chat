package com.stanthinking.im.server.actions.helpers;

import com.stanthinking.im.common.Message;
import com.stanthinking.im.common.Parameters;
import com.stanthinking.im.common.Sender;

final public class DatabaseHelpers {
    public static void sendSQLExceptionError(Sender sender) {
        sender.send(new Message("server", "LoginFailed", Parameters.of("reason", "Database error")));
    }
}
