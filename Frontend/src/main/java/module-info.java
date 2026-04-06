module com.rummyq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;

    opens com.rummyq to javafx.fxml;
    opens com.rummyq.view to javafx.fxml;
    opens com.rummyq.model to javafx.fxml;

    exports com.rummyq;
    exports com.rummyq.view;
    exports com.rummyq.model;
}
