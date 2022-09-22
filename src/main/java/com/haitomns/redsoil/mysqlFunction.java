package com.haitomns.redsoil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
            return result.next();
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

    public static boolean bloodAddDonorAndBlood(String donationOrganization,String donorName,String donorGender,String donorAge,String donorPhone,String donorOccupation,String donorAddress,String donorEmail,String patientName,String donorId,String diseaseList,String previousBloodDonatedStatus,String previouslyDonatedDate, String weight,String bp,String hb,String respSys,String cvs,String giSystem,String other,String fit,String unit,String abo,String rh,String hiv,String hcb,String hbsag,String vdrl,String bloodExpiryDate){
        try {
            //TODO: If one table is inserted and then other is no inserted then it will cause problem
            stmt = connect.createStatement();
            stmt.executeUpdate("INSERT INTO `redsoildb`.`blooddonationuserdata`(`ID`,`Blood_Donation_Orgnization`,`Donor_Name`,`Gender`,`Age`,`Occupation`,`Address`,`Phone`,`Email`,`Patient_Name`,`Donor_ID`,`Date_Of_Creation`)VALUES(Null,'" + donationOrganization + "','" + donorName + "','" + donorGender + "','" + donorAge + "','" + donorOccupation + "','" + donorAddress + "','" + donorPhone + "','" + donorEmail + "','" + patientName + "','" + donorId + "',CURRENT_TIMESTAMP);");
            result = stmt.executeQuery("SELECT ID FROM blooddonationuserdata ORDER BY ID DESC LIMIT 1");
            int donorID = 0;
            while (result.next()) {
                donorID = result.getInt("ID");
            }
            stmt.executeUpdate("INSERT INTO `redsoildb`.`blooddonationtestingdetails`(`ID`,`Donor_ID`,`Previously_Donated`,`Previously_Donated_Date`,`Diseases`,`Weight`,`BP`,`HB`,`Resp_Sys`,`Cvs`,`Gi_System`,`Other`,`Fit`,`Unit`,`ABO`,`RH`,`HIV`,`HBsAg`,`HCV`,`VDRL`,`Expiry_date`)VALUES(Null,'" + donorID + "','" + previousBloodDonatedStatus + "','" + previouslyDonatedDate + "','" + diseaseList + "','" + weight + "','" + bp + "','" + hb + "','" + respSys + "','" + cvs + "','" + giSystem + "','" + other + "','" + fit + "','" + unit + "','" + abo + "','" + rh + "','" + hiv + "','" + hbsag + "','" + hcb + "','" + vdrl + "','" + bloodExpiryDate + "');");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static ObservableList<bloodFindTableModel> bloodDonationView(String sqlQuery){
        ObservableList<bloodFindTableModel> bloodDonationList = FXCollections.observableArrayList();
        try{
            stmt = connect.createStatement();
            result = stmt.executeQuery(sqlQuery);
            while (result.next()) {
                String donorId = result.getString("Donor_ID");
                String donorName = result.getString("Donor_Name");
                String donorPhone = result.getString("Phone");
                String abo = result.getString("ABO");
                String rh = result.getString("RH");
                String unit = result.getString("Unit");
                String dateOfCreation = result.getString("Date_Of_Creation");
                String bloodExpiryDate = result.getString("Expiry_date");

                bloodDonationList.add(new bloodFindTableModel(donorId, donorName, donorPhone, abo, rh, unit, dateOfCreation, bloodExpiryDate));
            }
            return bloodDonationList;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ObservableList<bloodDonationTableModel> bloodDonationAddTable(){
        ObservableList<bloodDonationTableModel > bloodDonationData = FXCollections.observableArrayList();
        try{
            stmt = connect.createStatement();
            result = stmt.executeQuery("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Gender, Phone, ABO, RH, Unit FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID;");
            while (result.next()) {
                String donorId = result.getString("Donor_ID");
                String donorName = result.getString("Donor_Name");
                String donorGender = result.getString("Gender");
                String donorPhone = result.getString("Phone");
                String abo = result.getString("ABO");
                String rh = result.getString("RH");
                String unit = result.getString("Unit");

                bloodDonationData.add(new bloodDonationTableModel(donorId, donorName, donorGender, donorPhone, abo, rh, unit));
            }
            return bloodDonationData;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<Integer> bloodDonationViewCount(){
        List<Integer> bloodDonationList = new ArrayList<>();
        try{
            stmt = connect.createStatement();
            result = stmt.executeQuery("select * from bloodtypestotal;");
            while (result.next()) {
                bloodDonationList.add(result.getInt("count(ABO)"));
            }
            return bloodDonationList;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<Integer> bloodStatusCount(){
        List<Integer> bloodDonationList = new ArrayList<>();
        try{
            stmt = connect.createStatement();
            result = stmt.executeQuery("select * from bloodstatustotal;");
            while (result.next()) {
                bloodDonationList.add(result.getInt("count(Donor_ID)"));
            }
            return bloodDonationList;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean removeBloodDonation(String donerToRemove){
        try{
            stmt = connect.createStatement();
            stmt.executeUpdate("call removeBlood('"+donerToRemove+"');");
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static List<String> fetchUpdateData(String donorId){
        try{
            List<String> updateData = new ArrayList<>();

            stmt = connect.createStatement();
            result = stmt.executeQuery("select * FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where blooddonationuserdata.Donor_ID = '"+donorId+"';");
            while (result.next()) {
                updateData.add(result.getString("ID"));
                updateData.add(result.getString("Blood_Donation_Orgnization"));
                updateData.add(result.getString("Donor_Name"));
                updateData.add(result.getString("Gender"));
                updateData.add(result.getString("Age"));
                updateData.add(result.getString("Occupation"));
                updateData.add(result.getString("Address"));
                updateData.add(result.getString("Phone"));
                updateData.add(result.getString("Email"));
                updateData.add(result.getString("Patient_Name"));
                updateData.add(result.getString("Donor_ID"));
                updateData.add(result.getString("Previously_Donated"));
                updateData.add(result.getString("Previously_Donated_Date"));
                updateData.add(result.getString("Diseases"));
                updateData.add(result.getString("Weight"));
                updateData.add(result.getString("BP"));
                updateData.add(result.getString("HB"));
                updateData.add(result.getString("Resp_Sys"));
                updateData.add(result.getString("Cvs"));
                updateData.add(result.getString("Gi_System"));
                updateData.add(result.getString("Other"));
                updateData.add(result.getString("Fit"));
                updateData.add(result.getString("Unit"));
                updateData.add(result.getString("ABO"));
                updateData.add(result.getString("RH"));
                updateData.add(result.getString("HIV"));
                updateData.add(result.getString("HBsAg"));
                updateData.add(result.getString("HCV"));
                updateData.add(result.getString("VDRL"));
                updateData.add(result.getString("Expiry_date"));
            }
            System.out.println(updateData);
            return updateData;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
