package com.stanthinking.im.server;

import static org.junit.Assert.*;

import com.stanthinking.im.common.Message;
import com.stanthinking.im.common.Parameters;
import com.stanthinking.im.common.User;
import java.util.Arrays;
import org.junit.Test;

/**
 *
 * @author Stanislav Peshterliev
 */
public class LoginTest extends AbstractServerTest {

    @Test
    public void testLoginSuccessful() throws Exception {
        send(new Message("test", "Login", "", null, Parameters.of("password", "123")));

        Message message = receive();

        assertEquals(new Message("server", "LoginSuccessful", message.authtoken, null,
                Parameters.of("users", "",
                            "username", "test",
                            "email", "test@example.com",
                            "password", User.cryptPassword("123"))),
                message);

        message = receive();
        assertEquals(new Message("server", "UserOnline", "", Arrays.asList("all"), Parameters.of("user", "test")), message);
    }

    @Test
    public void testLoginFailed() throws Exception {
        send(new Message("invalid", "Login", "", null, Parameters.of("password", "123")));

        Message message = receive();

        assertEquals(new Message("server", "LoginFailed", Parameters.of("reason", "Invalid credentials")), message);
    }
}
