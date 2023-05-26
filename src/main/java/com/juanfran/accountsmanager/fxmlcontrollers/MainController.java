package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MainController {

    //  COMPONENTES GRÁFICOS
    @FXML
    public Button buttonAccounts;

    //  DEPENDENCES

    //  Logger lo utilizamos para informar al usuario de lo que ocurre en la aplicación
    private final Logger logger;

    //  CAMPOS

    //  CONSTRUCTOR PRINCIPAL DE LA CLASE
    public MainController(){
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    //  MÉTODOS

    /**
     * Este método se encarga de hacer navegar a la aplicación
     * de la vista main a la vista Accounts
     * @param actionEvent
     */
    public void navigateToAccountsView(ActionEvent actionEvent) {
        try {
            ((BorderPane) this.buttonAccounts.getScene().getRoot()).setCenter(ViewServiceProvider.getFXMLoaderFromView("AccountsView.fxml").load());
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de hacer navegar
     * a la vista dashboard de la aplicación
     * @param actionEvent
     */
    public void navigateToDashBoard(ActionEvent actionEvent) {
        try {
            ((BorderPane) this.buttonAccounts.getScene().getRoot()).setCenter(ViewServiceProvider.getFXMLoaderFromView("DashBoardView.fxml").load());
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
    }
}