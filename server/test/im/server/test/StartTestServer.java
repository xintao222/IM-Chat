package im.server.test;

import im.server.Application;
import im.server.model.ConnectionFactory;

/**
 *
 * @author Stanislav Peshterliev
 */
public class StartTestServer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory.load("db/connection.test.properties");

        Application server = new Application(4444);
        server.start();
    }
}
