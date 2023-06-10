module com.juanfran.accountsmanager.accounts_manager {
    requires javafx.controls;
    requires javafx.fxml;

    requires log4j;

    requires java.sql;

    requires javax.mail.api;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.iconsfivetwofive;

    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires javafx.swing;

    requires google.api.services.customsearch.v1.rev86;
    requires google.api.client;
    requires google.api.client.jackson2;
    requires com.google.common;
    requires com.google.api.client.auth;
    requires com.google.api.client.http.apache.v2;
    requires com.google.errorprone.annotations;

    requires CipherLibrary;
    requires SqlServerLibrary;
    requires java.desktop;
    requires com.google.api.client;
    requires com.google.api.client.json.jackson2;

    opens com.juanfran.accountsmanager to javafx.fxml;
    exports com.juanfran.accountsmanager;
    exports com.juanfran.accountsmanager.fxmlcontrollers;
    exports com.juanfran.accountsmanager.managers;
    exports com.juanfran.accountsmanager.daos;
    exports com.juanfran.accountsmanager.di;
    opens com.juanfran.accountsmanager.fxmlcontrollers to javafx.fxml;
}