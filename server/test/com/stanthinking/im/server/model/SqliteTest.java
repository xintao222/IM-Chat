package com.stanthinking.im.server.model;

import com.stanthinking.im.server.model.ConnectionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.*;

/**
 *
 * @author Stanislav Peshterliev
 */
public class SqliteTest {
    @Test
    public void testGetConnection() throws Exception {
        ConnectionFactory.setDriver("org.sqlite.JDBC");
        ConnectionFactory.setUrl("jdbc:sqlite:db/im_test.db");
        Connection conn = ConnectionFactory.getConnection();

        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists people;");
        stat.executeUpdate("create table people (name, occupation);");
        PreparedStatement prep = conn.prepareStatement(
                "insert into people values (?, ?);");

        prep.setString(1, "Gandhi");
        prep.setString(2, "politics");
        prep.addBatch();
        prep.setString(1, "Turing");
        prep.setString(2, "computers");
        prep.addBatch();
        prep.setString(1, "Wittgenstein");
        prep.setString(2, "smartypants");
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);

        ResultSet rs = stat.executeQuery("select * from people;");

        String[][] expected = new String[][]{{"Gandhi", "politics"}, {"Turing", "computers"}, {"Wittgenstein", "smartypants"}};
        int i = 0;
        while (rs.next()) {
            assertEquals(expected[i][0], rs.getString("name"));
            assertEquals(expected[i][1], rs.getString("occupation"));
            i++;
        }
        
        rs.close();
        conn.close();
    }
}