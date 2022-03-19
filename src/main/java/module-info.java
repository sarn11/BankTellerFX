module com.example.banktellerfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.banktellerfx to javafx.fxml;
    exports com.example.banktellerfx;
}