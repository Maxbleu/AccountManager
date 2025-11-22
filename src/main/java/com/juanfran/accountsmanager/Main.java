package com.juanfran.accountsmanager;

import  com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.managers.PasswordManager;
import com.juanfran.accountsmanager.managers.UserManager;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main extends Application {
    private static final Logger logger = Logger.getLogger("Inicio del programa");

    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        stage.getIcons().add(new Image(this.getClass().getResource("Images/logoApp.png").toString()));
        stage.setScene(ViewServiceProvider.getScene("LoginView.fxml"));
        stage.show();
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/main/resources/com/juanfran/accountsmanager/Properties/log4j.properties");
        OrchestratorProyectDependences.loadProyectDependences(logger, Main.class.getResource("Properties/beans.xml").toString());
        ((PasswordManager) OrchestratorProyectDependences.getService(PasswordManager.class)).selectPasswords("SelectPasswords");
        ((UserManager) OrchestratorProyectDependences.getService(UserManager.class)).selectUsers("SelectUsers");
        launch();
    }
}