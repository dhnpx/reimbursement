package db;

import java.sql.*;
import java.util.Properties;
import javax.sql.DataSource;
import java.io.*;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Database {

    private static Properties prop = new Properties();
    private static String username;
    private static String password;
    private static DataSource ds;

    private static void getProps() {
        try {
            prop.load(Database.class.getClassLoader().getResourceAsStream("db.properties"));
            username = prop.getProperty("user");
            password = prop.getProperty("pass");
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        Database.getProps();
        if (ds == null) {
            HikariConfig config = new HikariConfig();
            config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
            config.setUsername(username);
            config.setPassword(password);
            config.addDataSourceProperty("databaseName", "project1");

            ds = new HikariDataSource(config);
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private Database() {}
}
