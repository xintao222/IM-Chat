package com.stanthinking.im.common;

import com.stanthinking.im.util.Hashs;

/**
 *
 * @author Stanislav Peshterliev
 */
public class User {
    public User(String username, String email, String cryptedPassword) {
        this.username = username;
        this.cryptedPassword = cryptedPassword;
        this.email = email;
    }
    
    public final String username;
    public final String cryptedPassword;
    public final String email;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        if ((this.cryptedPassword == null) ? (other.cryptedPassword != null) : !this.cryptedPassword.equals(other.cryptedPassword)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.username != null ? this.username.hashCode() : 0);
        hash = 31 * hash + (this.cryptedPassword != null ? this.cryptedPassword.hashCode() : 0);
        hash = 31 * hash + (this.email != null ? this.email.hashCode() : 0);
        return hash;
    }

    public static String cryptPassword(String password) {
        return Hashs.sh1(password);
    }
}