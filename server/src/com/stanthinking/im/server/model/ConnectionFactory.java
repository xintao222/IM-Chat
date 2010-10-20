package com.stanthinking.im.server.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.stanthinking.im.util.Pair;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Stanislav Peshterliev
 */
public class ConnectionFactory {
    private static String driver = null;
    private static String url = null;
    private static Map<Pair<String, String>, Connection> connections;

    public static void setDriver(String driver) {
        ConnectionFactory.driver = driver;
    }

    public static void setUrl(String url) {
        ConnectionFactory.url = url;
    }    

    public static Connection getConnection(String driver, String url) throws SQLException {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(url);
        
        Pair<String, String> pair = Pair.of(driver, url);

        if (connections == null) {
            connections = Maps.newHashMap();
        }

        if (!connections.containsKey(pair)) {
            try {
                Class.forName(driver);
                connections.put(pair, DriverManager.getConnection(url));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return connections.get(pair);
    }

    public static Connection getConnection() throws SQLException {        
        return getConnection(driver, url);
    }

    public static void load(String propertiesFile) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setDriver(properties.getProperty("driver"));
        setUrl(properties.getProperty("url"));
    }
}
