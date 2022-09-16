package com.bisecthosting.mcordlink.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class connection {
    public Connection conn = null;

    public Connection make_connection() throws SQLException {
        this.conn = DriverManager.getConnection
                ("jdbc:mysql://66.248.193.2/mc155219?user=mc155219&password=3beb9537c7");
        return this.conn;
    }

    public void execute_query(String query) throws Exception {
        InitialContext ctx = new InitialContext();

    }


}
