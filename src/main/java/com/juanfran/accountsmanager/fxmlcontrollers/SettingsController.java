package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.Main;
import com.juanfran.accountsmanager.daos.AccountDAOS;
import com.juanfran.accountsmanager.daos.PasswordDAOS;
import com.juanfran.accountsmanager.daos.UserDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.models.UserModel;
import com.juanfran.accountsmanager.services.CipherServiceProvider;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Optional;

public class SettingsController {

    //  COMPONENTES GRÁFICOS
    @FXML
    public Button buttonChangePassword;
    @FXML
    public Button buttonLogOut;
    @FXML
    public Button buttonDeleteUser;

    //  DEPENDENCES
    private final Logger logger;
    private final AccountDAOS accountDAOS;
    private final UserDAOS userDAOS;
    private final PasswordDAOS passwordDAOS;

    //  CAMPOS
    public static UserModel userRegistered;

    //  CONTRUCTOR PRINCIPAL DE LA CLASE
    public SettingsController(){
        this.logger = OrchestratorProyectDependences.getLogger();
        this.accountDAOS = (AccountDAOS) OrchestratorProyectDependences.getService(AccountDAOS.class);
        this.userDAOS = (UserDAOS) OrchestratorProyectDependences.getService(UserDAOS.class);
        this.passwordDAOS = (PasswordDAOS) OrchestratorProyectDependences.getService(PasswordDAOS.class);
    }

    //  MÉTODOS

    /**
     * Este método se encarga de navegar
     * desde la vista home a la vista login
     * para iniciar sesión con otra cuenta
     * @param actionEvent
     */
    public void logOutApplication(ActionEvent actionEvent) {
        this.logger.info("La sesión del usuario" + userRegistered.getIdUser() + " ha sido cerrada");
        ((Stage)this.buttonDeleteUser.getScene().getWindow()).close();
        ((Main) OrchestratorProyectDependences.getService(Main.class)).start(new Stage());
    }

    /**
     * Este método se encarga de cambiar
     * la contraseña del usuario
     * @param actionEvent
     */
    public void changeUserPassword(ActionEvent actionEvent) {
        Stage stageChangePassword = new Stage();

        VBox vBoxStageChangePassword = new VBox();

        vBoxStageChangePassword.setAlignment(Pos.TOP_LEFT);
        vBoxStageChangePassword.setStyle("-fx-background-color: #313649;");
        vBoxStageChangePassword.setPrefHeight(275);
        vBoxStageChangePassword.setPrefWidth(342);

        Label textChangePassword = new Label("Introduce una nueva contraseña");
        textChangePassword.setFont(Font.font(16));
        textChangePassword.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        textChangePassword.setPadding(new Insets(30,0,0,30));

        vBoxStageChangePassword.getChildren().add(textChangePassword);

        PasswordField textFieldOldPassword = new PasswordField();
        textFieldOldPassword.setPromptText("OldPassword");
        textFieldOldPassword.setPrefWidth(272);
        textFieldOldPassword.setPrefHeight(35);
        textFieldOldPassword.setFont(Font.font(14));
        VBox.setMargin(textFieldOldPassword,new Insets(20,30,0,30));
        textFieldOldPassword.getStyleClass().add("inputPasswordRegisterUserView");

        vBoxStageChangePassword.getChildren().add(textFieldOldPassword);

        PasswordField textFieldNewPassword = new PasswordField();
        textFieldNewPassword.setPromptText("NewPassword");
        textFieldNewPassword.setPrefWidth(272);
        textFieldNewPassword.setPrefHeight(35);
        textFieldNewPassword.setFont(Font.font(14));
        VBox.setMargin(textFieldNewPassword,new Insets(20,30,0,30));
        textFieldNewPassword.getStyleClass().add("inputPasswordRegisterUserView");

        vBoxStageChangePassword.getChildren().add(textFieldNewPassword);

        Button buttonChangeUserPassword = new Button("Acept");
        buttonChangeUserPassword.setPrefHeight(35);
        buttonChangeUserPassword.setPrefWidth(75);
        VBox.setMargin(buttonChangeUserPassword,new Insets(20,0,0,30));
        buttonChangeUserPassword.getStyleClass().add("buttonSignInLoginView");
        buttonChangeUserPassword.setOnAction(actionEvent1 -> {

            String hashedUserPassword = CipherServiceProvider.hashToHex(textFieldOldPassword.getText());
            if(this.passwordDAOS.isTheSamePassword(userRegistered.getIdPassword(),hashedUserPassword)){
                String hashNewUserPassword = CipherServiceProvider.hashToHex(textFieldNewPassword.getText());
                this.passwordDAOS.updatePassword(userRegistered.getIdPassword(),hashNewUserPassword);
                stageChangePassword.close();
                this.logger.info("La contraseña del usuario " + userRegistered.getIdUser() + " ha sido cambiada");
            }else{
                ViewServiceProvider.launchAlert(Alert.AlertType.WARNING.toString(),"La contraseña antigua no coinciden", Alert.AlertType.ERROR);
            }
        });

        vBoxStageChangePassword.getChildren().add(buttonChangeUserPassword);

        Scene sceneChangePassword = new Scene(vBoxStageChangePassword);
        stageChangePassword.setScene(sceneChangePassword);
        stageChangePassword.setTitle("Change user password");
        sceneChangePassword.getStylesheets().add(Main.class.getResource("StyleSheet/StyleSheet.css").toString());
        stageChangePassword.setResizable(false);
        stageChangePassword.show();
    }

    /**
     * Este método se encarga de eliminar
     * la cuenta del usuario
     * @param actionEvent
     */
    public void deleteUser(ActionEvent actionEvent) {
        Optional<ButtonType> option = ViewServiceProvider.launchAlert("Warning","¿Estas seguro que deseas eliminar esta cuenta? No volverás a recuperar tus datos", Alert.AlertType.CONFIRMATION);
        if(option.get() == ButtonType.OK){
            for(int i = 0; i<this.accountDAOS.getAccounts().size(); i++){
                this.passwordDAOS.removePassword(this.accountDAOS.getAccounts().get(i).getIdPassword());
                this.accountDAOS.removeAccount(this.accountDAOS.getAccounts().get(i));
            }
            this.passwordDAOS.removePassword(userRegistered.getIdPassword());
            this.userDAOS.removeUser(userRegistered);
            logOutApplication(new ActionEvent());
        }
    }
}
