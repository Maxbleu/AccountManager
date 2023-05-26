package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IAccountDAOS;
import com.juanfran.accountsmanager.interfaces.IPasswordDAOS;
import com.juanfran.accountsmanager.models.AccountModel;
import com.juanfran.accountsmanager.models.PasswordModel;
import com.juanfran.accountsmanager.models.UserModel;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;
import org.cipherLibrary.Ciphers.AESCipherLibrary;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddAndModifyAccountController implements Initializable {

    //  COMPONENTES GRÁFICOS
    @FXML
    public TextField inputTestWebSiteTitle;
    @FXML
    public TextField inputTestUserName;
    @FXML
    public TextField inputTestPassword;
    @FXML
    public TextField inputTestWebSiteURL;
    @FXML
    public TextField inputTestEmail;
    @FXML
    public Button buttonAcept;
    @FXML
    public ImageView iconWebSite;

    //  DEPENDENCES
    private final Logger logger;
    private final IAccountDAOS accountDAOS;
    private final IPasswordDAOS passwordDAOS;

    //  CAMPOS
    public static UserModel userRegistered;

    //  CONSTRUCTOR PRINCIPAL DE LA CLASE

    public AddAndModifyAccountController() {
        this.logger = OrchestratorProyectDependences.getLogger();
        this.accountDAOS = (IAccountDAOS) OrchestratorProyectDependences.getService(IAccountDAOS.class);
        this.passwordDAOS = (IPasswordDAOS) OrchestratorProyectDependences.getService(IPasswordDAOS.class);
    }

    //  MÉTODOS

    /**
     * Este método se encarga de insertar
     * la cuenta que ha registrado el
     * usuario en los inputText en la
     * aplicación
     * @param actionEvent
     */
    public void keepAccount(ActionEvent actionEvent) {

        //  Comprobamos que todos los inputText no están vacios
        if(!this.inputTestWebSiteURL.getText().equals("") && !this.inputTestEmail.getText().equals("") && !this.inputTestUserName.getText().equals("") && !this.inputTestPassword.getText().equals("") && !this.inputTestWebSiteTitle.getText().equals("")){

            this.logger.info("Insertamos la cuenta de " + this.inputTestWebSiteTitle.getText() + ", " + this.inputTestUserName.getText() + " en la base de datos");

            //  Insertamos la contraseña de la cuenta personal en la aplicación

            byte[] cipheredPassword = AESCipherLibrary.cifrarAES(this.inputTestPassword.getText(), userRegistered.getSecretKey());
            String cipheredPasswordString = Arrays.toString(cipheredPassword);

            PasswordModel accountPassword = new PasswordModel(cipheredPasswordString);
            this.passwordDAOS.registerNewPassword(accountPassword);

            //  Insertarmos la cuenta personal en la base de datos
            AccountModel newPersonalAccount = new AccountModel(this.inputTestWebSiteURL.getText(),this.inputTestWebSiteTitle.getText(),this.inputTestUserName.getText(),this.inputTestEmail.getText(),accountPassword);
            this.accountDAOS.registerNewAccount(newPersonalAccount);

            //  Navegamos a la página AccountView
            navigateToAccountsView(actionEvent);

        }else{

            //  Notificamos al usuario que todos los campos del formulario deben estar rellenados
            ViewServiceProvider.launchAlert(Alert.AlertType.WARNING.toString(),"El formulario debe estar rellenado", Alert.AlertType.WARNING);
        }
    }

    /**
     * Este método se encarga volvernos
     * a la vista AccountsView
     * @param actionEvent
     */
    public void navigateToAccountsView(ActionEvent actionEvent) {
        try {
            ((BorderPane) this.buttonAcept.getScene().getRoot()).setCenter(ViewServiceProvider.getFXMLoaderFromView("AccountsView.fxml").load());
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //  Hacer funcionalidad de que por cada letra que escribe en el inputText WebSiteTitle
        //  hacemos una búsqueda custom, obtenemos los resultados y los mostramos en forma de lista
        //  pop app

        //  Además, cuando hagas click en una opción del pop app el icono la página
        //  se pondrá en el imageView y también pondremos en el inputTextWebSiteURL
        //  la url de la página seleccionada
    }
}
