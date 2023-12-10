module com.example.aims {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.aims to javafx.fxml;
    exports com.example.aims;

    opens com.example.views to javafx.fxml;
    exports com.example.views;

    opens com.example.views.home;
    exports com.example.views.home;

    opens com.example.views.cart;
    exports com.example.views.cart;

    opens com.example.views.invoice;
    exports com.example.views.invoice;

    opens com.example.views.payment;
    exports com.example.views.payment;

    opens com.example.views.popup;
    exports com.example.views.popup;

    opens com.example.views.shipping;
    exports com.example.views.shipping;
}