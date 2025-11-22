package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.Main;
import com.juanfran.accountsmanager.daos.PasswordDAOS;
import com.juanfran.accountsmanager.daos.UserDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IGmailManager;
import com.juanfran.accountsmanager.interfaces.IPasswordDAOS;
import com.juanfran.accountsmanager.interfaces.IUserDAOS;
import com.juanfran.accountsmanager.managers.AccountManager;
import com.juanfran.accountsmanager.managers.GmailManager;
import com.juanfran.accountsmanager.models.UserModel;
import com.juanfran.accountsmanager.services.CipherServiceProvider;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class LoginController {

    //  COMPONENTES GRÁFICOS
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField textFieldUserName;
    @FXML
    public Label textFieldError;
    @FXML
    public Label labelForgotMyPassword;


    //  DEPENDENCIAS DE LA CLASE

    //  USERDAOS se encarga de comprobar que un usuario con el nombre que ha introducido el cliente
    private final IUserDAOS userDAOS;

    //  PASSWORDDAOS se encarga de comprobar que la contraseña del usuario es correcta
    private final IPasswordDAOS passwordDAOS;

    //  Logger se encarga mantener informado al desarrollador de todo lo que ocurre en la aplicación
    private final Logger logger;


    //  CONSTRUCTOR PRINCIPAL DE LA CLASE
    public LoginController(){
        this.userDAOS = (IUserDAOS) OrchestratorProyectDependences.getService(UserDAOS.class);
        this.passwordDAOS = (IPasswordDAOS) OrchestratorProyectDependences.getService(PasswordDAOS.class);
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    //  MÉTODOS

    /**
     * Este método se encarga de comprobar si podemos realizar
     * el proceso de login en la aplicación en base a que
     * los inputs no están vacios y que el nombre de usuario
     * está registrado en la aplicación.
     * @param actionEvent
     */
    public void login(ActionEvent actionEvent) {

        //  Comprobamos que si los inputs están vacios
        if(!this.textFieldUserName.getText().equals("") && !this.passwordField.getText().equals("")){

            //  Comprobamos si el nombre de usuario introducido se encuentra registrado en la aplicación
            if(this.userDAOS.thereUserRegisteredWithSameUserName(this.textFieldUserName.getText())){

                UserModel user = this.userDAOS.getUserByName(this.textFieldUserName.getText());

                //  Hasheamos la contraseña para compararla
                String passwordHexHash = CipherServiceProvider.hashToHex(this.passwordField.getText());

                //  Comprobamos si la contraseña introducida es correcta
                if(this.passwordDAOS.isTheSamePassword(user.getIdPassword(),passwordHexHash)){

                    //  Nos movemos a la página principal de la aplicación
                    this.logger.info("Has iniciado sesión en la aplicación");

                    //  Creamos la clave simétrica y la insertamos en el usuario
                    user.setSecretKey(CipherServiceProvider.generateAsemetricKey(user.getIdUser().toString()));

                    //  Pasaremos el usuario registrado a el campo estático de la clase AccountManager y al controlador AddAndModifyAccountCont
                    //  EXP:
                    //  Realizo esta acción ya que hacer login en la aplicación va de la mano con
                    //  que el usuario tenga acceso a la información por lo cual debemos pasarle
                    //  a la clase dicho valor para poder descifrar los valores que recibe de la
                    //  base de datos para que el usuario pueda verlos.
                    AccountManager.userRegistered = user;

                    //  Además, requerimos también el usuario registrado cuando añadimos o
                    //  modificamos una cuenta personal para cifrar su información
                    //  antes de ser enviada a la base de datos.
                    AddAndModifyAccountController.userRegistered = user;

                    AccountsController.userRegistered = user;

                    MainController.userRegistered = user;

                    SettingsController.userRegistered = user;

                    //  Cargamos las cuentas del usuario en la aplicación
                    ((AccountManager) OrchestratorProyectDependences.getService(AccountManager.class)).selectAccountsByUserId(" { Call SelectAccountsByUserId (?) } ", user.getIdUser());

                    //  Navegamos a la página principal de la aplicación
                    Scene mainScene = ViewServiceProvider.getScene("MainView.fxml");
                    ((Stage)this.textFieldError.getScene().getWindow()).setResizable(true);
                    ((Stage)this.textFieldError.getScene().getWindow()).setScene(mainScene);
                }else{

                    //  La contraseña introducida es incorrecta
                    this.logger.info("La contraseña introducida es incorrecta");
                    ViewServiceProvider.launchAlert("Warning","El usuario o la contraseña son incorrectos", Alert.AlertType.WARNING);
                }
            }else{

                //  El nombre de usuario no está registrado en la aplicación
                this.logger.info("El usuario introducido no está registrado en la aplicación");
                ViewServiceProvider.launchAlert("Warning","El usuario o la contraseña son incorrectos", Alert.AlertType.WARNING);
            }
        }else{

            //  Los inputs del formulario están vacios
            this.logger.info("No puede haber ningún campo del formulario sin rellenar");
            ViewServiceProvider.launchAlert("Warning","No pueden haber ningún campo del formulario sin rellenar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Este método se encarga de navegar hacia la vista
     * he olvidado mi contraseña
     * @param event
     */
    public void navigateToIForgotMyPasswordView(MouseEvent event) {
        ((Stage)this.textFieldError.getScene().getWindow()).setScene(ViewServiceProvider.getScene("ForgotMyPasswordView.fxml"));
    }

    /**
     * Este método se encarga de navegar hacia la vista
     * registrar un nuevo usuario
     * @ActionEvent actionEvent
     */
    public void navigateToRegisterUserView(ActionEvent actionEvent) {
        ((Stage)this.textFieldError.getScene().getWindow()).setScene(ViewServiceProvider.getScene("RegisterUserView.fxml"));
    }
}