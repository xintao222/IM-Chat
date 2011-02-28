package im.server.test;

import im.server.model.ConnectionFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.Arrays;
import im.common.Parameters;
import im.common.Message;
import im.server.model.UserModel;
import im.common.User;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Stanislav Peshterliev
 */
public class SendTest extends AbstractServerTest {

    @Before
    public void setUp() throws SQLException {
        ConnectionFactory.load("db/connection.test.properties");

        UserModel.delete("test1");

        UserModel.create(new User("test1", "test1@example.com", User.cryptPassword("123")));
    }

    @After
    public void tearDown() throws SQLException {
        UserModel.delete("test1");
    }

    @Test
    public void testSend() throws Exception {
        String username = "test1";

        send(new Message(username, "Login", "", null, Parameters.of("password", "123")));

        Message message = receive();

        assertEquals("LoginSuccessful", message.action);

        String receiver = "test1";
        String authtoken = message.authtoken;

        send(new Message(username, "Send", authtoken, Arrays.asList(receiver), Parameters.of("content", "hellow world" )));

        message = receive();

        assertEquals("UserOnline", message.action);

        message = receive();

        assertEquals("Received", message.action);
        assertEquals("hellow world", message.parameters.get("content"));

        send(new Message(username, "Logout", authtoken));
    }
}
