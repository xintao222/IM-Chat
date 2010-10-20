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
public class LogoutTest extends AbstractServerTest {

    @Test
    public void testLogout() throws Exception {
        String authtoken = login();
        
        send(new Message("test", "Logout", authtoken));
        socket.close();

        setUpClass();

        login();
    }

    public String login() throws Exception {
        send(new Message("test", "Login", "", null, Parameters.of("password", "123")));

        Message message = receive();
        String authtoken = message.authtoken;

        assertEquals(new Message("server", "LoginSuccessful", message.authtoken, null,
                Parameters.of("users", "",
                            "username", "test",
                            "email", "test@example.com",
                            "password", User.cryptPassword("123"))),
                             message);

        message = receive();
        assertEquals(new Message("server", "UserOnline", "", Arrays.asList("all"), Parameters.of("user", "test")), message);

        return authtoken;
    }
}
