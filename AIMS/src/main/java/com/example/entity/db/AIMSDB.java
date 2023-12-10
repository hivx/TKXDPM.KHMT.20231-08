package com.example.entity.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import java.sql.Connection;
import com.example.utils.*;

public class AIMSDB {

	private static Logger LOGGER = Utils.getLogger(Connection.class.getName());
	private static Connection connect;

    public static Connection getConnection() {
        if (connect != null) return connect;
        try {
            String hostName = "localhost";
            String dbName = "aims";
            String userName = "root";
            String password = "123456";
            connect = getMysqlConnection(hostName, dbName, userName, password);
            LOGGER.info("Connect database successfully");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        } 
        return connect;
    }

    public static Connection getMysqlConnection(String hostName, String dbName, String userName, String password) throws SQLException {
        String connectionUrl = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useUnicode=true&characterEncoding=utf-8";
        return DriverManager.getConnection(connectionUrl, userName, password);
    }
    

    public static void main(String[] args) {
        AIMSDB.getConnection();
    }
}
