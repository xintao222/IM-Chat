package im.server.test.model;

import im.server.model.UserModel;
import im.server.model.ConnectionFactory;
import im.common.User;
import im.server.model.ConnectionFactory;
import im.server.model.UserModel;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stanislav Peshterliev
 */
public class UserModelTest {

    @Before
    public void setUp() {
        ConnectionFactory.load("db/connection.test.properties");
    }

    @Test
    public void testCRUD() throws SQLException {
        User user = new User("stanislav", "stanislav@example.com", User.cryptPassword("123"));
        UserModel.create(user);

        User dbUser = UserModel.findByUsername("stanislav");
        assertEquals(user, dbUser);

        UserModel.delete("stanislav");
        
        dbUser = UserModel.findByUsername("stanislav");
        assertNull(dbUser);
    }
}