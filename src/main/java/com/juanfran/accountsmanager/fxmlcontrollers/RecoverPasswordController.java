package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.daos.PasswordDAOS;
import com.juanfran.accountsmanager.daos.UserDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IPasswordDAOS;
import com.juanfran.accountsmanager.interfaces.IUserDAOS;
import com.juanfran.accountsmanager.models.UserModel;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Timer;

public class RecoverPasswordController {

    //  COMPONENTES GRÁFICOS
    @FXML
    public Button buttonAceptar;
    @FXML
    public Label labelTimer;
    @FXML
    public TextField textFieldCode;

    //  DEPENDENCIAS

    //  PASSWORDDAOS se encarga de obtener el código de recuperación de la contraseña
    private final IPasswordDAOS passwordDAOS;

    //  USERDAOS se encarga de obtener el usuario a través del email que ha introducido el usuario previamente
    private final IUserDAOS userDAOS;

    //  Logger se encarga de mantener el informado al usuario de todo lo que ocurre en la aplicación
    private final Logger logger;


    //  CAMPOS

    private final Timer timer = new Timer();
    private final Integer minutes = 15;
    private final Integer seconds = 1;
    public static String customerEmail;
    private Integer generatedCode;


    //  CONSTRUCTOR PRINCIPAL DE LA CLASE

    public RecoverPasswordController(){
        this.userDAOS = (IUserDAOS) OrchestratorProyectDependences.getService(UserDAOS.class);
        this.passwordDAOS = (IPasswordDAOS) OrchestratorProyectDependences.getService(PasswordDAOS.class);
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    //  MÉTODOS

    /**
     * Este método se encarga de comprobar
     * si los datos introducidos por el
     * usuario son números.
     * @param insertedUserText
     * @return
     */
    private boolean isNumber(String insertedUserText){
        try{
            Integer.parseInt(insertedUserText);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * Este método se encarga de comprobar si el código de recuperación
     * de la contraseña es correcto aunque antes debe de comprobar si
     * los inputs del formulario no están vacios
     * @param actionEvent
     */
    public void isCodeCorrect(ActionEvent actionEvent) {

        String code = this.textFieldCode.getText();

        //  Comprobamos si el campo no está vacio
        if(!code.equals("")){

            //  Comprobamos si no ha expirado el tiempo de validez del código de recuperación
            if(minutes>0 && seconds>0){

                //  Comprobamos si los datos introducidos por el usuario son números
                if(isNumber(code)){

                    //  Comprobamos si el código introducido por el usuario es el igual al generado
                    Integer userCode = Integer.parseInt(code);
                    if(generatedCode.equals(userCode)){

                        //  Navegamos hacia la vista ChangePasswordView.fxml para cambiar la contraseña del usuario
                        this.logger.info("El usuario ha acertado el código");
                        ChangePasswordController.costumerEmail = customerEmail;
                        ((Stage) this.buttonAceptar.getScene().getWindow()).setScene(ViewServiceProvider.getScene("ChangePasswordView.fxml"));

                    }else{

                        //  El usuario ha introducido un código incorrecto
                        this.logger.info("El usuario ha introducido un código incorrecto: "+ code);
                        ViewServiceProvider.launchAlert("Warning","El código introducido es incorrecto", Alert.AlertType.ERROR);
                    }

                }else{

                    //  Los datos introducidos solo pueden ser números
                    this.logger.info("Los datos introducidos solo pueden ser números");
                    ViewServiceProvider.launchAlert("Warning","Los datos introducidos solo pueden ser números", Alert.AlertType.ERROR);

                }

            }else{

                //  Ha expirado el tiempo de validez del código de recuperación
                this.logger.info("El tiempo de validez del código ha expirado");
                ViewServiceProvider.launchAlert("Warning","Genera un nuevo código el anterior ha expirado", Alert.AlertType.ERROR);
            }
        }else{

            //  El campo del código esta vacio
            this.logger.info("El usuario ha enviado un campo vacio");
            ViewServiceProvider.launchAlert("Warning","Rellene el campo por favor", Alert.AlertType.ERROR);
        }
    }

    public void initialize(){

        //  Obtenemos el usuario a través del email
        UserModel user = this.userDAOS.getUserByEmail(customerEmail);

        //  A través del usuario obtenido obtenemos el código de recuperación de la contraseña
        generatedCode = this.passwordDAOS.getRecoverPasswordCode(user.getIdPassword());

        //  CORREGIR EL TIMER
        /*TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                if(seconds == 0) {
                    if(minutes == 0) {
                        _timer.cancel();
                    } else {
                        minutes--;
                        seconds = 59;
                        String timer = minutes.toString()+":"+seconds.toString();
                        labelTimer.setText(timer);
                    }
                } else {
                    seconds--;
                    String timer = minutes.toString()+":"+seconds.toString();
                    labelTimer.setText(timer);
                }
            }
        };
        _timer.schedule(tarea, 0, 1000);*/
    }
}
