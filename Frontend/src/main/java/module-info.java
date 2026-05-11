module com.rummyq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires javafx.graphics;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.rummyq to javafx.fxml;
    opens com.rummyq.view to javafx.fxml;
    opens com.rummyq.model to javafx.fxml;
    opens com.rummyq.api to com.fasterxml.jackson.databind;

    exports com.rummyq;
    exports com.rummyq.view;
    exports com.rummyq.model;
}
