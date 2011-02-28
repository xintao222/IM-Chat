package im.server.test;

import static org.junit.Assert.*;

import im.common.Message;
import im.common.Parameters;
import im.server.model.UserModel;
import org.junit.Test;

/**
 *
 * @author Stanislav Peshterliev
 */
public class RegisterTest extends AbstractServerTest {

    @Test
    public void testRegistrationSuccessful() throws Exception {
        send(new Message("registration_test", "Register", "", null,
                Parameters.of("email", "registration_test@example.com", "password", "123")));

        Message message = receive();

        assertEquals(new Message("server", "RegistrationSuccessful", message.authtoken), message);
        UserModel.delete("registration_test");
    }

    @Test
    public void testRegistrationFailed() throws Exception {
        Message message = null;

        String[][] params = new String[][] {
            {"test", "test@example.com", "123", "User with this username already exists"},
            {"test1", "testexample.com", "123", "Not valid email"},
            {"test1", "test@example.com", "", "Password required"},
            {"", "test@example.com", "123", "Username required"}
        };

        UserModel.delete("test1");

        for (String[] param : params) {
            send(new Message(param[0], "Register", "", null, Parameters.of("email", param[1], "password", param[2])));
            message = receive();
            assertEquals(new Message("server", "RegistrationFailed", Parameters.of("reason", param[3])), message);            
        }
    }
}
