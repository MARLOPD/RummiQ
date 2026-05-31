module com.rummyq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires javafx.graphics;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires com.fasterxml.jackson.annotation;

    opens com.rummyq to javafx.fxml, ch.qos.logback.classic;
    opens com.rummyq.view to javafx.fxml;
    opens com.rummyq.model to javafx.fxml, com.fasterxml.jackson.databind;
    opens com.rummyq.api to com.fasterxml.jackson.databind;
    opens com.rummyq.websocket.dto to com.fasterxml.jackson.databind;

    exports com.rummyq;
    exports com.rummyq.view;
    exports com.rummyq.model;
}