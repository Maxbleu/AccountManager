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

    requires CipherLibrary;
    requires SqlServerLibrary;

    opens com.juanfran.accountsmanager to javafx.fxml;
    exports com.juanfran.accountsmanager;
    exports com.juanfran.accountsmanager.fxmlcontrollers;
    exports com.juanfran.accountsmanager.managers;
    exports com.juanfran.accountsmanager.daos;
    exports com.juanfran.accountsmanager.di;
    opens com.juanfran.accountsmanager.fxmlcontrollers to javafx.fxml;
}