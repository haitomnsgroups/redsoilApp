module com.haitomns.redsoil {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.haitomns.redsoil to javafx.fxml;
    exports com.haitomns.redsoil;
}