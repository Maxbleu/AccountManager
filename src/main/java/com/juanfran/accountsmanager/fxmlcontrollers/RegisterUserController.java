package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.daos.PasswordDAOS;
import com.juanfran.accountsmanager.daos.UserDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IPasswordDAOS;
import com.juanfran.accountsmanager.interfaces.IUserDAOS;
import com.juanfran.accountsmanager.managers.AccountManager;
import com.juanfran.accountsmanager.managers.GmailManager;
import com.juanfran.accountsmanager.models.PasswordModel;
import com.juanfran.accountsmanager.models.UserModel;
import com.juanfran.accountsmanager.services.CipherServiceProvider;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class RegisterUserController {

    //  COMPONENTES GRÁFICOS
    @FXML
    public TextField textFieldUserName;
    @FXML
    public TextField textFieldEmail;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField passwordFieldRepeatedPassword;
    @FXML
    public Button buttonAccept;

    //  DEPENDENCES

    //  USERDAOS lo utilizaremos para obtener el usuario a través del email que nos han proporcionado anteriormente
    private final IUserDAOS userDAOS;

    //  PASSWORDDAOS lo utilizaremos para obtener el código de recuperación
    private final IPasswordDAOS passwordDAOS;

    //  Logger lo utilizaremos para mantener informado al usuario de lo que ocurre en la aplicación
    private final Logger logger;
    private final GmailManager gmailManager;


    //  CONSTRUCTOR PRINCIPAL DE LA CLASE
    public RegisterUserController(){
        this.userDAOS = (IUserDAOS) OrchestratorProyectDependences.getService(UserDAOS.class);
        this.passwordDAOS = (IPasswordDAOS) OrchestratorProyectDependences.getService(PasswordDAOS.class);
        this.gmailManager = (GmailManager) OrchestratorProyectDependences.getService(GmailManager.class);
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    //  MÉTODOS

    /**
     * Este método se encarga de realizar las comprobaciones necesarios para
     * crear un nuevo usuario en la aplicación, en caso de que se cumplan
     * el usuario se registrará en la aplicación, en caso contrario
     * se le avisará al usuario de su error.
     * @param actionEvent
     */
    public void createAccount(ActionEvent actionEvent) {

        //  Comprobar que todos los campos del formulario no están vacios
        if(!this.textFieldEmail.getText().equals("") && !this.textFieldUserName.getText().equals("") && !this.passwordField.getText().equals("") && !this.passwordFieldRepeatedPassword.getText().equals("")){

            //  Comprobar que no hay ninguna cuenta asociada al email introducido
            if(!this.userDAOS.thereUserRegisteredWithSameEmail(this.textFieldEmail.getText())){

                //  Comprobar que no hay ninguna cuenta con el mismo nombre de usuario
                if(!this.userDAOS.thereUserRegisteredWithSameUserName(this.textFieldUserName.getText())){

                    //  Comprobar que las contraseñas introducidas son las mismas
                    if(this.passwordField.getText().equals(this.passwordFieldRepeatedPassword.getText())){

                        //  Hasheamos la contraseña
                        String passwordHexHash = CipherServiceProvider.hashToHex(this.passwordField.getText());

                        //  Creo un modelo PasswordModel
                        PasswordModel password = new PasswordModel(passwordHexHash);

                        //  Creo un modelo UserModel
                        UserModel newUser = new UserModel(this.textFieldEmail.getText(),this.textFieldUserName.getText(),password.getIdPassword());

                        //  Creamos e insertamos la clave simétrica en el usuario
                        newUser.setSecretKey(CipherServiceProvider.generateAsemetricKey(newUser.getIdUser().toString()));

                        //  Colocamos el usuario registrado en AccountManager para tener acceso a la clave simétrica
                        AccountManager.userRegistered = newUser;
                        AddAndModifyAccountController.userRegistered = newUser;
                        AccountsController.userRegistered = newUser;
                        MainController.userRegistered = newUser;
                        SettingsController.userRegistered = newUser;

                        //  Almacenamos la contraseña en la base de datos
                        this.passwordDAOS.registerNewPassword(password);

                        //  Almacenamos el usuario en la base de datos
                        this.userDAOS.registerNewUser(newUser);

                        //  Enviamos un mensaje de bienvenida al usuario
                        this.gmailManager.sendWelcomeEmail(newUser.getEmail());

                        //  Navegar a la vista Main
                        Scene mainScene = ViewServiceProvider.getScene("MainView.fxml");
                        ((Stage)this.buttonAccept.getScene().getWindow()).setResizable(true);
                        ((Stage)this.buttonAccept.getScene().getWindow()).setScene(mainScene);

                    }else{

                        //  Las contraseñas son diferentes
                        this.logger.info("Las contraseñas introducidas no coinciden");
                        ViewServiceProvider.launchAlert(Alert.AlertType.ERROR.toString(),"Las contraseñas introducidas no coinciden", Alert.AlertType.ERROR);
                    }

                }else{

                    //  Existe una cuenta con ese mismo nombre
                    this.logger.info("Ya hay una cuenta con ese nombre");
                    ViewServiceProvider.launchAlert(Alert.AlertType.ERROR.toString(),"Ya existe una cuenta con ese nombre.", Alert.AlertType.ERROR);
                }

            }else{

                //  Existe una cuenta registrada con ese correo
                this.logger.info("Existe una cuenta con ese correo");
                ViewServiceProvider.launchAlert(Alert.AlertType.ERROR.toString(),"Ya existe una cuenta con ese correo", Alert.AlertType.ERROR);
            }

        }else{

            //  Los campos del formulario están sin rellenar
            this.logger.info("Los campos del formulario están sin rellenar");
            ViewServiceProvider.launchAlert("Warning","No pueden haber ningún campo del formulario sin rellenar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Este método se encarga de cancelar el proceso de registro de un nuevo usuario en la aplicación
     * @param actionEvent
     */
    public void cancel(ActionEvent actionEvent) {
        ((Stage)this.buttonAccept.getScene().getWindow()).setScene(ViewServiceProvider.getScene("LoginView.fxml"));
    }
}