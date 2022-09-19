package com.haitomns.redsoil;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

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

    public static boolean companyUpdate(String companyName, String companyAddress, String companyPhone, String companyUsername, String companyPassword){
        try {
            stmt = connect.createStatement();
            //TODO: Now it is updating via id change it to something reliable
            stmt.executeUpdate("UPDATE company SET Company_Name = '" + companyName + "', Company_Address = '" + companyAddress + "', Company_Phone = '" + companyPhone + "' WHERE ID = 1");
            stmt.executeUpdate("UPDATE login SET username = '" + companyUsername + "', password = '" + companyPassword + "' WHERE ID = 1");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static List<Object> companyDetails(){
        try {
            String companyName = "Haitomn's Group", companyAddress = "Knowhere", companyPhone = "9809204764", companyUsername = "haitomns", companyPassword = "redSoil";
            stmt = connect.createStatement();
            result = stmt.executeQuery("select Company_Name, Company_Address, Company_Phone from company limit 1;");
            while (result.next()) {
                companyName = result.getString("Company_Name");
                companyAddress = result.getString("Company_Address");
                companyPhone = result.getString("Company_Phone");
            }

            stmt = connect.createStatement();
            result = stmt.executeQuery("select username, password from login limit 1;");
            while (result.next()) {
                companyUsername = result.getString("username");
                companyPassword = result.getString("password");
            }

            return List.of(companyName, companyAddress, companyPhone, companyUsername, companyPassword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean bloodAddDonorAndBlood(String donorId, String  donorName, String  donorGender, String  donorAge, String  donorPhone, String donorDob, String donorOccupation, String donorAddress, String donorEmail, String diseaseList, String lastDonatedDate, String weight, String bp, String hb, String respSys,String cvs, String giSystem, String  other, String fit, String unit, String abo, String rh, String hiv, String  hcb, String hbsag, String  vdrl){
        try {
            stmt = connect.createStatement();
            stmt.executeUpdate("INSERT INTO `redsoildb`.`blooddonationuserdata` (`ID`, `Doner_ID`, `Doner_Name`, `Date_Of_Birth`, `Age`, `Gender`, `Occupation`, `Address`, `Phone`, `Email`,`Date_Of_Creation`)VALUES(NULL, '" + donorId + "', '" + donorName + "', '" + donorDob + "', '" + donorAge + "', '" + donorGender + "', '" + donorOccupation + "', '" + donorAddress + "', '" + donorPhone + "', '" + donorEmail + "', CURRENT_TIMESTAMP);");
            result = stmt.executeQuery("SELECT ID FROM blooddonationuserdata ORDER BY ID DESC LIMIT 1");
            int donerID = 0;
            while (result.next()) {
                donerID = result.getInt("ID");
            }
            stmt.executeUpdate("INSERT INTO `redsoildb`.`blooddonationtestingdetails` (`ID`, `Doner_ID`, `Previously_Donated`, `Diseases`, `Weight`, `BP`, `HB`, `Resp_Sys`, `Cvs`, `Gi_System`, `Other`,`Fit`, `Unit`, `ABO`, `RH`, `HIV`, `HBsAg`, `HCV`, `VDRL`) VALUES(NULL, '" + donerID + "', '"+ lastDonatedDate +"', '" + diseaseList + "', '" + weight + "', '" + bp + "', '" + hb + "', '" + respSys + "', '" + cvs + "', '" + giSystem + "', '" + other + "', '" + fit + "', '" + unit + "', '" + abo + "', '" + rh + "', '" + hiv + "', '" + hbsag + "', '" + hcb + "', '" + vdrl + "');");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static ObservableList<bloodFindTableModel> bloodDonationView(){
        ObservableList<bloodFindTableModel> bloodDonationList = FXCollections.observableArrayList();
        try{
            stmt = connect.createStatement();
            result = stmt.executeQuery("SELECT blooddonationuserdata.Doner_ID, Doner_Name, Gender, Phone, Diseases, ABO, RH, HIV, HBsAg, HCV, VDRL, Date_Of_Creation FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Doner_ID;");
            while (result.next()) {
                String donorId = result.getString("Doner_ID");
                String donorName = result.getString("Doner_Name");
                String donorGender = result.getString("Gender");
                String donorPhone = result.getString("Phone");
                String diseaseList = result.getString("Diseases");
                String abo = result.getString("ABO");
                String rh = result.getString("RH");
                String hiv = result.getString("HIV");
                String hbsag = result.getString("HBsAg");
                String hcv = result.getString("HCV");
                String vdrl = result.getString("VDRL");
                String dateOfCreation = result.getString("Date_Of_Creation");

                bloodDonationList.add(new bloodFindTableModel(donorId, donorName, donorGender, donorPhone, diseaseList, abo, rh, hiv, hbsag, hcv, vdrl, dateOfCreation));
            }
            return bloodDonationList;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
