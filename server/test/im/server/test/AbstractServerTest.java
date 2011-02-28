package im.server.test;

import im.common.Message;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import im.common.User;
import im.server.model.ConnectionFactory;
import im.server.model.UserModel;
import org.junit.BeforeClass;

/**
 *
 * @author Stanislav Peshterliev
 */
abstract public class AbstractServerTest {

    static final String ADDRESS = "localhost";
    static final int PORT = 4444;
    static PrintWriter out;
    static BufferedReader in;
    static Socket socket;

    @BeforeClass
    public static void setUpClass() throws Exception {
        socket = new Socket(ADDRESS, PORT);

        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        ConnectionFactory.load("db/connection.test.properties");

        UserModel.delete("test");
        UserModel.create(new User("test", "test@example.com", User.cryptPassword("123")));
    }

    public static void send(Message message) throws Exception {
        out.println(message.xmlize());
        out.flush();
    }

    public static Message receive() throws Exception {
        return Message.parse(in.readLine());
    }
}
