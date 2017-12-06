package com.personal.oyl.code.example.storm.jdbc.spout;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class ConnectionManager {
    private final static BasicDataSource ds = new BasicDataSource();
    static {
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/test");
        ds.setUsername("root");
        ds.setPassword("password123");
        ds.setMaxActive(5);
        ds.setInitialSize(1);
        ds.setMaxWait(1000);
        ds.setRemoveAbandonedTimeout(60);
        ds.setTestOnBorrow(true);
        ds.setValidationQuery("select 1");
    }
    
    public static Connection getConn() throws SQLException {
        return ds.getConnection();
    }
    
    public static void close(Connection conn) throws SQLException {
        if (null != conn) {
            conn.close();
            conn = null;
        }
    }
}
