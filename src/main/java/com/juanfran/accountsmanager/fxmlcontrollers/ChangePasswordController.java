package com.juanfran.accountsmanager.fxmlcontrollers;


import com.juanfran.accountsmanager.daos.PasswordDAOS;
import com.juanfran.accountsmanager.daos.UserDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IPasswordDAOS;
import com.juanfran.accountsmanager.interfaces.IUserDAOS;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.cipherLibrary.Ciphers.HashingLibrary;

public class ChangePasswordController {

    //  COMPONENTES GRÁFICOS
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField passwordFieldRepeatedPassword;
    @FXML
    public Button buttonConfirmChange;

    //  DEPENDENCIAS

    //  UserDAOS lo utilizaremos para obtener el usuario en base a su correo.
    private final IUserDAOS userDAOS;

    //  PasswordDAOS lo utilizaremos para realizar la modificación de la contraseña
    private final IPasswordDAOS passwordDAOS;

    //  Logger lo utilizaremos para informar en todo momento sobre lo que ocurre en la app
    private final Logger logger;

    //  CAMPOS
    public static String costumerEmail;

    //  CONSTRUCTOR PRINCIPAL DE LA CLASE
    public ChangePasswordController(){
        this.userDAOS = (IUserDAOS) OrchestratorProyectDependences.getService(UserDAOS.class);
        this.passwordDAOS = (IPasswordDAOS) OrchestratorProyectDependences.getService(PasswordDAOS.class);
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    //  MÉTODOS

    /**
     * Este método se encarga de realizar las comprobaciones necesarias
     * para saber si podemos modificar la contraseña en base a
     * si los inputs estan vacios o las contraseñas introducias
     * son diferentes, en caso contrario se realizará la modificación
     * @param actionEvent
     */
    public void changePassword(ActionEvent actionEvent) {

        //  Comprobamos que los passwordField no estan vacios
        if(!this.passwordField.getText().equals("") && !this.passwordFieldRepeatedPassword.getText().equals("")){

            //  Comprobamos que las contraseñas introducidas por el usuario son correctas
            if(this.passwordField.getText().equals(this.passwordFieldRepeatedPassword.getText())){

                this.logger.info("Actualizamos la contraseña");

                //  Hasheamos la contraseña
                byte[] passwordBytes = HashingLibrary.hash(this.passwordField.getText());
                String passwordHashHex = HashingLibrary.toHexString(passwordBytes);

                //  Actualizamos la contraseña del usuario
                this.passwordDAOS.updatePassword(this.userDAOS.getUserByEmail(costumerEmail).getIdPassword(),passwordHashHex);

                //  Navegamos la vista login de la aplicación una vez modificada la contraseña
                ((Stage)this.buttonConfirmChange.getScene().getWindow()).setScene(ViewServiceProvider.getScene("LoginView.fxml"));

            }else{

                //  En caso de que las contraseñas no sean iguales informamos al usuario
                this.logger.error("Las contraseñas introducidas por el usuario no coinciden");
                ViewServiceProvider.launchAlert("Error","Las contraseñas introducidas no coinciden", Alert.AlertType.ERROR);
            }
        }else{

            //  En caso de que los inputs esten vacios informaremos al usuario
            ViewServiceProvider.launchAlert("Warning","Debe de estar rellenados todos los campos del formulario", Alert.AlertType.WARNING);
            this.logger.error("El formulario no debe estar vacio");
        }
    }

}
