module MicrosoftTeamManagementApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.web;
    requires com.almasb.fxgl.scene;
    requires java.net.http;
    requires jakarta.json;
    requires jdk.httpserver;
    requires java.desktop;

    opens controller to javafx.fxml;
    opens entity to javafx.base;
    exports controller;
    exports feature;
    opens feature to javafx.fxml;
    exports entity;
}