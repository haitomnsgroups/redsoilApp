package com.haitomns.redsoil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class mysqlFunction {
    static Connection connect = null;
    static Statement stmt = null;
    static ResultSet result = null;

    public static String databaseUsername;
    public static String databasePassword;
    public static String portNumber;
    public static String databasePath;

    public static boolean mysqlDatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            databaseCredentialsReader();
            String db_url = "jdbc:mysql://localhost:"+portNumber+"/redsoilDB";

            connect = DriverManager.getConnection(db_url, databaseUsername, databasePassword);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean mysqlConnectionCheck() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            databaseCredentialsReader();
            String db_url = "jdbc:mysql://localhost:"+portNumber+"/";

            connect = DriverManager.getConnection(db_url, databaseUsername, databasePassword);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void databaseCredentialsReader() throws FileNotFoundException {
        File databaseFile = new File("redSoilDatabaseConnection.rdfs");
        Scanner databaseFileReader = new Scanner(databaseFile);
        if(databaseFile.exists()){
            portNumber = databaseFileReader.nextLine();
            databaseUsername = databaseFileReader.nextLine();
            databasePassword = databaseFileReader.nextLine();
            databasePath = databaseFileReader.nextLine();
        }
        else{
            redsoilMain databaseConnection = new redsoilMain();
            databaseConnection.openDBConfigurator();
        }
        databaseFileReader.close();
    }

    public static boolean loginCheck(String username, String password){
        try {
            stmt = connect.createStatement();
            result = stmt.executeQuery("SELECT username, password from login where username = '" + username + "' AND password = '" + password + "'");
            return result.next();
        } catch (Exception e) {
            showError("Login Error", "Login Error", "Login Error");
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
            showError("Update Error", "Update Error", "Update Error");
            return false;
        }
    }

    public static List<Object> companyDetails(){
        try {
            String companyName = "Haitomn's Group", companyAddress = "Knowhere", companyPhone = "9801126858", companyUsername = "haitomns", companyPassword = "redSoil";
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
            showError("Company Details Error", "Company Details Error", "Company Details Error");
            return null;
        }
    }

    public static boolean checkDonorID(String donorId){
        try {
            stmt = connect.createStatement();
            result = stmt.executeQuery("select donor_id from blooddonationuserdata where donor_id = '" + donorId + "'");
            return result.next();
        } catch (Exception e) {
            showError("Donor ID Error", "Donor ID Error", "Donor ID Error");
            return false;
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
            showError("Blood Add Error", "Blood Add Error", "Blood Add Error");
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
            showError("Blood Donation View Error", "Blood Donation View Error", "Blood Donation View Error");
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
            showError("Blood Donation Add Table Error", "Blood Donation Add Table Error", "Blood Donation Add Table Error");
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
            showError("Blood Donation View Count Error", "Blood Donation View Count Error", "Blood Donation View Count Error");
            return null;
        }
    }

    //TODO: Change
    public static List<Integer> bloodStatusCount(){
        List<Integer> bloodDonationList = new ArrayList<>();
        try{
            stmt = connect.createStatement();
            result = stmt.executeQuery("SELECT * FROM redsoildb.bloodstautstotal;");
            while (result.next()) {
                bloodDonationList.add(result.getInt("donorTotal"));
            }
            return bloodDonationList;
        }
        catch (Exception e){
            showError("Blood Status Count Error", "Blood Status Count Error", "Blood Status Count Error");
            return null;
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
            return updateData;
        }
        catch (Exception e){
            showError("Blood Update Error", "Blood Update Error", "Blood Update Error");
            return null;
        }
    }

    public static ObservableList<bloodFindTableModel> removeDataView(){
        try{
            ObservableList<bloodFindTableModel> removedData = FXCollections.observableArrayList();

            stmt = connect.createStatement();
            result = stmt.executeQuery("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood != \"No\";");
            while (result.next()) {
                String donorId = result.getString("Donor_ID");
                String donorName = result.getString("Donor_Name");
                String donorPhone = result.getString("Phone");
                String abo = result.getString("ABO");
                String rh = result.getString("RH");
                String unit = result.getString("Unit");
                String dateOfCreation = result.getString("Date_Of_Creation");
                String expiryDate = result.getString("Expiry_date");

                removedData.add(new bloodFindTableModel(donorId, donorName, donorPhone, abo, rh, unit, dateOfCreation, expiryDate));
            }
            return removedData;
        }
        catch (Exception e){
            showError("Blood Remove Error", "Blood Remove Error", "Blood Remove Error");
            return null;
        }
    }

    public static boolean bloodComponentAdd(String donorId, String prbcs, String prbc, String ffp, String platelets, String prp, String cryoppt){
        try{
            stmt = connect.createStatement();
            stmt.executeUpdate("UPDATE bloodcomponent SET `prbcs` = '"+prbcs+"',`prbc` = '"+prbc+"',`ffp` = '"+ffp+"', `platelets` ='"+platelets+"',`prp` = '"+prp+"',`cryoppt` = '"+cryoppt+"' where Donor_ID = (select ID from blooddonationuserdata where Donor_ID = '"+donorId+"');");
            return true;
        }
        catch (Exception e){
            showError("Blood Component Add Error", "Blood Component Add Error", "Blood Component Add Error");
            return false;
        }
    }

    public static boolean wholeBloodComponentAdd(String donorId, String wholeBlood){
        try{
            stmt = connect.createStatement();
            stmt.executeUpdate("UPDATE bloodcomponent SET `discard_blood` = '"+wholeBlood+"' where Donor_ID = (select ID from blooddonationuserdata where Donor_ID = '"+donorId+"');");
            return true;
        }
        catch (Exception e){
            showError("Blood Component Add Error", "Blood Component Add Error", "Blood Component Add Error");
            return false;
        }
    }

    public static List<String> bloodComponentInitialize(String donorID){
        try{
            List<String> bloodComponentData = new ArrayList<>();

            stmt = connect.createStatement();
            result = stmt.executeQuery("select * from bloodcomponent where Donor_ID = (select ID from blooddonationuserdata where Donor_ID = '"+donorID+"');");
            while (result.next()) {
                bloodComponentData.add(result.getString("prbcs"));
                bloodComponentData.add(result.getString("prbc"));
                bloodComponentData.add(result.getString("ffp"));
                bloodComponentData.add(result.getString("platelets"));
                bloodComponentData.add(result.getString("prp"));
                bloodComponentData.add(result.getString("cryoppt"));
                bloodComponentData.add(result.getString("discard_blood"));
            }
            return bloodComponentData;
        }
        catch (Exception e){
            showError("Blood Component Initialize Error", "Blood Component Initialize Error", "Blood Component Initialize Error");
            return null;
        }
    }

    public static boolean updateDonor(String donorIdToUpdate, String donationOrganization, String donorName, String donorGender, String donorAge, String donorOccupation, String donorAddress, String donorPhone, String donorEmail, String patientName, String donorId, String previouslyDonated, String previouslyDonatedDate, String diseases, String weight, String bp, String hb, String respSys, String cvs, String giSystem, String other, String fit, String unit, String abo, String rh, String hiv, String hbsAg, String hcv, String vdrl, String expiryDate){
        try{
            stmt = connect.createStatement();

            stmt.executeUpdate("update blooddonationtestingdetails set `Previously_Donated`= '"+previouslyDonated+"', `Previously_Donated_Date` = '"+previouslyDonatedDate+"',`Diseases` = '"+diseases+"',`Weight` = '"+weight+"',`BP` = '"+bp+"',`HB` = '"+hb+"',`Resp_Sys` = '"+respSys+"',`Cvs` = '"+cvs+"',`Gi_System` = '"+giSystem+"',`Other` = '"+other+"',`Fit` = '"+fit+"',`Unit`= '"+unit+"',`ABO` = '"+abo+"',`RH` = '"+rh+"',`HIV` = '"+hiv+"',`HBsAg` = '"+hbsAg+"',`HCV` = '"+hcv+"',`VDRL` = '"+vdrl+"',`Expiry_date` = '"+expiryDate+"'where Donor_ID = (select ID from blooddonationuserdata where Donor_ID = '"+donorIdToUpdate+"');");
            stmt.executeUpdate("update blooddonationuserdata set`Blood_Donation_Orgnization` = '"+donationOrganization+"',`Donor_Name` = '"+donorName+"',`Gender` = '"+donorGender+"',`Age` = '"+donorAge+"',`Occupation` = '"+donorOccupation+"',`Address` = '"+donorAddress+"',`Phone` = '"+donorPhone+"',`Email` = '"+donorEmail+"',`Patient_Name` = '"+patientName+"',`Donor_ID` = '"+donorId+"'where Donor_ID = '"+donorIdToUpdate+"'; ");
            return true;
        }
        catch (Exception e){
            showError("Blood Update Error", "Blood Update Error", e.toString());
            return false;
        }
    }

    public static boolean resetPassword(String phone, String username, String password){
        try{
            stmt = connect.createStatement();
            result = stmt.executeQuery("SELECT * FROM redsoildb.company;");
            while (result.next()) {
                if(phone.equals(result.getString("Company_Phone"))){
                    stmt.executeUpdate("update login set username = '"+username+"', password = '"+password+"' where id = 1;");
                    return true;
                }
            }
            return false;
        }
        catch (Exception e){
            showError("Password Reset Error", "Password Reset Error", e.toString());
            return false;
        }
    }

    public static boolean restoreDatabase(){
        try{
            stmt = connect.createStatement();
            stmt.executeUpdate("create database redsoilDB;");
            stmt.executeUpdate("use redsoilDB;");

            String restore_file_path;

            databaseCredentialsReader();
            File restore_full_path = new File("redsoilDatabase/redSoilMainDB.sql");
            restore_file_path = restore_full_path.getAbsolutePath();

            String[] restoreCmd = new String[]{databasePath+"mysql ", "--user=" +databaseUsername, "--password=" +databasePassword, "--port=" +portNumber, "redsoilDB", "-e", "source "+restore_file_path};

            Process runtimeProcess = Runtime.getRuntime().exec(restoreCmd);
            int processComplete = runtimeProcess.waitFor();

            return processComplete == 0;

        }
        catch (Exception e){
            showError("Database Restore Error", "Database Restore Error", e.toString());
            return false;
        }
    }

    public static boolean backupDatabase(String path){
        try{
            databaseCredentialsReader();

            File backup_full_path = new File(path);
            String backup_file_path = backup_full_path.getAbsolutePath();

            String[] backupCmd = new String[]{databasePath+"mysqldump ", "--user=" +databaseUsername, "--password=" +databasePassword, "--port=" +portNumber, "redsoilDB", "-r", backup_file_path};

            Process runtimeProcess = Runtime.getRuntime().exec(backupCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                DateTimeFormatter tf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                LocalDateTime now = LocalDateTime.now();

                String backup_path = "redsoilBackup/backup_" + tf.format(now) + ".sql";
                Process process_backup;
                String[] backupCmdredsoil = new String[]{databasePath+"mysqldump ", "--user=" +databaseUsername, "--password=" +databasePassword, "--port=" +portNumber, "redsoilDB", "-r", backup_path};
                process_backup = Runtime.getRuntime().exec(backupCmdredsoil);
                process_backup.waitFor();

                return true;
            } else {
                return false;
            }
        }
        catch (Exception e){
            showError("Database Backup Error", "Database Backup Error", e.toString());
            return false;
        }
    }

    public static Boolean restoredb(String path){
        try{
            databaseCredentialsReader();

            String[] restoreCmd = new String[]{databasePath+"mysql ", "--user=" +databaseUsername, "--password=" +databasePassword, "--port=" +portNumber, "redsoilDB", "-e", "source "+path};

            Process runtimeProcess = Runtime.getRuntime().exec(restoreCmd);
            int processComplete = runtimeProcess.waitFor();

            return processComplete == 0;
        }
        catch (Exception e){
            showError("Database Restore Error", "Database Restore Error", e.toString());
            return false;
        }
    }

    public static void closeDatabase(){
        try{
            connect.close();
        }
        catch (Exception e){
            showError("Database Close Error", "Database Close Error", e.toString());
        }
    }

    public static void showError(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
