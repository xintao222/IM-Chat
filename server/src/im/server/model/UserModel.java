package im.server.model;

import im.common.User;
import java.sql.*;

/**
 *
 * @author Stanislav Peshterliev
 */
public class UserModel {

    public static User findByUsername(String username) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement prep = null;
        User user = null;
        try {
            prep = conn.prepareStatement("select * from users where username = ?;");
            prep.setString(1, username);

            rs = prep.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("crypted_password"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (prep != null) {
                prep.close();
            }
        }

        return user;
    }

    public static void create(User user) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement prep = null;

        try {
            prep = conn.prepareStatement("insert into users(username, email, crypted_password) values(?,?,?);");
            prep.setString(1, user.username);
            prep.setString(2, user.email);
            prep.setString(3, user.cryptedPassword);

            prep.executeUpdate();
        } finally {
            if (prep != null) {
                prep.close();
            }
        }
    }

    public static void delete(String username) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement prep = null;

        try {
            prep = conn.prepareStatement("delete from users where username = ?;");
            prep.setString(1, username);

            prep.executeUpdate();
        } finally {
            if (prep != null) {
                prep.close();
            }
        }
    }
}
