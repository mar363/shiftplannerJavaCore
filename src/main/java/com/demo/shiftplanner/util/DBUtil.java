package com.demo.shiftplanner.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static final String PROPERTIES_FILES="/application.properties";
    private static String url;
    private static String user;
    private static String password;

    static {
        try(InputStream in = DBUtil.class.getResourceAsStream(PROPERTIES_FILES)) {
            Properties props = new Properties();
            props.load(in);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
