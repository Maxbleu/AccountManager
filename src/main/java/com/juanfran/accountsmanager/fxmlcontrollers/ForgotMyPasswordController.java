package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.daos.PasswordDAOS;
import com.juanfran.accountsmanager.daos.UserDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.services.GeneratorRecoverPasswordCodeServiceProvider;
import com.juanfran.accountsmanager.interfaces.IGmailManager;
import com.juanfran.accountsmanager.interfaces.IPasswordDAOS;
import com.juanfran.accountsmanager.interfaces.IUserDAOS;
import com.juanfran.accountsmanager.managers.GmailManager;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class ForgotMyPasswordController {

    //  COMPONENTES GRÁFICOS
    @FXML
    public TextField textFieldGmail;
    @FXML
    public Button buttonRecoverPassword;

    //  DEPENDENCIAS

    //  GMAILMANAGER lo utilizaremos para enviar al usuario el código generado
    private final IGmailManager gmailManager;

    //  USERDAOS lo utilizaremos para obtener el código de contraseña
    private final IUserDAOS userDAOS;

    //  PASSWORDDAOS lo utilizaremos para a través del código de contraseña insertar el código de contraseña en la base de datos
    private final IPasswordDAOS passwordDAOS;

    //  Logger lo utilizaremos para informar al usuario constantemente de lo que ocurre en la aplicación
    private final Logger logger;


    //  CONSTRUCTOR PRINCIPAL DE LA CLASE
    public ForgotMyPasswordController() {
        this.gmailManager = (IGmailManager) OrchestratorProyectDependences.getService(GmailManager.class);
        this.userDAOS = (IUserDAOS) OrchestratorProyectDependences.getService(UserDAOS.class);
        this.passwordDAOS = (IPasswordDAOS) OrchestratorProyectDependences.getService(PasswordDAOS.class);
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    //  MÉTODOS

    /**
     * Este método se encarga de realizar las comprobaciones necesarias para
     * generar el código de recuperación de la contraseña y enviarsela al
     * usuario. En base a que el input de no esté vacio y que el email que
     * introduce el usuario está asociado con una cuenta en la aplicación
     * @param actionEvent
     */
    public void recoverPassword(ActionEvent actionEvent) {

        if(!this.textFieldGmail.getText().equals("")){
            if(this.userDAOS.thereUserRegisteredWithSameEmail(this.textFieldGmail.getText())){

                //  Generamos el codigo para recuperar la contraseña
                Integer codeGenerated = GeneratorRecoverPasswordCodeServiceProvider.getRecoverPasswordCode();

                //  Enviamos la contraseña a la base de datos
                Integer idPassword = this.userDAOS.getUserByEmail(this.textFieldGmail.getText()).getIdPassword();
                this.passwordDAOS.updateRecoverPasswordCode(idPassword,codeGenerated);

                //  Enviamos un email con la contraseña generada
                this.gmailManager.sendRecoverPasswordCodeEmail(this.textFieldGmail.getText(),codeGenerated);

                //  Guardamos el codigo generado para comparar si el usuario introduce el generado
                RecoverPasswordController.customerEmail = this.textFieldGmail.getText();

                //  Mostramos la pantalla para introducir el código
                ((Stage) this.buttonRecoverPassword.getScene().getWindow()).setScene(ViewServiceProvider.getScene("RecoverPasswordView.fxml"));

            }else{
                ViewServiceProvider.launchAlert("Error","Este email no se encuentra registrado en la aplicación", Alert.AlertType.ERROR);
                this.logger.error("El email introducido por el usuario está asociado con ningún usuario");
            }
        }else {
            ViewServiceProvider.launchAlert("Warning", "Debes introducir un email", Alert.AlertType.WARNING);
            this.logger.error("El input no puede estar vacio");
        }
    }

    /**
     * Este método se encarga de cancelar el proceso de
     * recuperacón de la contraseña
     * @param actionEvent
     */
    public void cancel(ActionEvent actionEvent) {
        ((Stage)this.buttonRecoverPassword.getScene().getWindow()).setScene(ViewServiceProvider.getScene("LoginView.fxml"));
        this.logger.info("El usuario ha cancelado el proceso de recuperación de su contraseña");
    }
}