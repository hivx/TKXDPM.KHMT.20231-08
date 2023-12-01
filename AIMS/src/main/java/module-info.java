module com.example.aims {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.aims to javafx.fxml;
    exports com.example.aims;

    opens com.example.views to javafx.fxml;
    exports com.example.views;
}