module com.rummyq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires java.net.http;      // para HttpClient (llamadas al backend)

    opens com.rummyq to javafx.fxml;
    opens com.rummyq.view to javafx.fxml;
    opens com.rummyq.model to javafx.fxml;

    exports com.rummyq;
    exports com.rummyq.view;
    exports com.rummyq.model;
    exports com.rummyq.service;
    exports com.rummyq.util;
}
