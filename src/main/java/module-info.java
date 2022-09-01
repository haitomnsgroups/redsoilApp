module com.haitomns.redsoil {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.haitomns.redsoil to javafx.fxml;
    exports com.haitomns.redsoil;
}