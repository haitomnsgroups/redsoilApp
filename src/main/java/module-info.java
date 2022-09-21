module com.haitomns.redsoil {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires com.jfoenix;


    opens com.haitomns.redsoil to javafx.fxml;
    exports com.haitomns.redsoil;
}