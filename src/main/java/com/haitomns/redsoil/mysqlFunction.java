package com.haitomns.redsoil;

import java.sql.*;

public class mysqlFunction {
    static Connection connect = null;
    static Statement stmt = null;
    static ResultSet result = null;

    public static boolean mysqlDatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String db_url = "jdbc:mysql://localhost:3307/redsoilDB";
            String db_username = "root";
            String db_password = "redSoil@1220";

            connect = DriverManager.getConnection(db_url, db_username, db_password);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean loginCheck(String username, String password){
        try {
            stmt = connect.createStatement();
            result = stmt.executeQuery("SELECT username, password from login where username = '" + username + "' AND password = '" + password + "'");
            if(result.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
