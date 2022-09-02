package com.haitomns.redsoil;

import java.sql.*;

public class mysqlFunction {
    public static boolean mysqlDatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String db_url = "jdbc:mysql://localhost:3307/redsoilDB";
            String db_username = "root";
            String db_password = "redSoil@1220";

            Connection connect = DriverManager.getConnection(db_url, db_username, db_password);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
