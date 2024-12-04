module com.example.darkness {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.darkness to javafx.fxml;
    exports com.example.darkness;
}