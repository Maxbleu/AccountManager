package com.juanfran.accountsmanager.fxmlcontrollers;

import com.google.api.services.customsearch.v1.model.Result;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IAccountDAOS;
import com.juanfran.accountsmanager.interfaces.IGmailManager;
import com.juanfran.accountsmanager.interfaces.IPasswordDAOS;
import com.juanfran.accountsmanager.models.AccountModel;
import com.juanfran.accountsmanager.models.PasswordModel;
import com.juanfran.accountsmanager.models.UserModel;
import com.juanfran.accountsmanager.services.CipherServiceProvider;
import com.juanfran.accountsmanager.services.GETCustomSearchsServiceProvider;
import com.juanfran.accountsmanager.services.GETIconWebSiteServiceProvider;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import org.apache.log4j.Logger;
import org.cipherLibrary.Ciphers.AESCipherLibrary;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddAndModifyAccountController implements Initializable {

    //  COMPONENTES GRÁFICOS
    @FXML
    public TextField inputTestWebSiteTitle;
    @FXML
    public TextField inputTestUserName;
    @FXML
    public PasswordField inputTestPassword;
    @FXML
    public TextField inputTestWebSiteURL;
    @FXML
    public TextField inputTestEmail;
    @FXML
    public Button buttonAcept;
    @FXML
    public ImageView iconWebSite;
    @FXML
    public ListView listViewSearchsFound;

    //  DEPENDENCES
    private final Logger logger;
    private final IAccountDAOS accountDAOS;
    private final IPasswordDAOS passwordDAOS;

    //  CAMPOS
    public static UserModel userRegistered;
    private AccountModel accountSelected;
    public final ObservableList<HBox> datosSearchsFound = FXCollections.observableArrayList();

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

            if(this.accountSelected == null){

                this.logger.info("Insertamos la cuenta de " + this.inputTestWebSiteTitle.getText() + ", " + this.inputTestUserName.getText() + " en la base de datos");

                //  Insertamos la contraseña de la cuenta personal en la aplicación

                byte[] cipheredPassword = AESCipherLibrary.cifrarAES(this.inputTestPassword.getText(), userRegistered.getSecretKey());
                String cipheredPasswordString = Arrays.toString(cipheredPassword);

                PasswordModel accountPassword = new PasswordModel(cipheredPasswordString);
                this.passwordDAOS.registerNewPassword(accountPassword);

                //  Insertarmos la cuenta personal en la base de datos
                AccountModel newPersonalAccount = new AccountModel(this.inputTestWebSiteURL.getText(),this.inputTestWebSiteTitle.getText(),this.inputTestUserName.getText(),this.inputTestEmail.getText(),accountPassword.getIdPassword());
                this.accountDAOS.registerNewAccount(newPersonalAccount);

            }else{

                this.logger.info("Modificamos la cuenta de " + this.inputTestWebSiteTitle.getText() + ", " + this.inputTestUserName.getText() + " en la base de datos");

                //  Modificamos los datos de la cuenta personal en la aplicación

                byte[] cipheredPassword = AESCipherLibrary.cifrarAES(this.inputTestPassword.getText(), userRegistered.getSecretKey());
                String cipheredPasswordString = Arrays.toString(cipheredPassword);
                this.passwordDAOS.updatePassword(this.accountSelected.getIdPassword(),cipheredPasswordString);

                accountSelected.setWebSiteAddress(this.inputTestWebSiteURL.getText());
                accountSelected.setUserName(this.inputTestUserName.getText());
                accountSelected.setEmail(this.inputTestEmail.getText());
                accountSelected.setWebSiteTitle(this.inputTestWebSiteTitle.getText());
                this.accountDAOS.updateAccount(accountSelected);

            }

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

    /**
     * Este método se encarga de cargar los datos
     * de una cuenta personal para modificar
     * los datos de esta
     * @param accountSelected
     * @param image
     */
    public void navigateToAccountViewToModifyAccount(AccountModel accountSelected ,Image image){
        this.accountSelected = accountSelected;
        this.iconWebSite.setImage(image);
        this.inputTestEmail.setText(accountSelected.getEmail());
        this.inputTestUserName.setText(accountSelected.getUserName());
        this.inputTestWebSiteTitle.setText(accountSelected.getWebSiteTitle());
        this.inputTestWebSiteURL.setText(accountSelected.getWebSiteAddress());

        String stringBytesCipheredPassword = this.passwordDAOS.getPasswordById(accountSelected.getIdPassword()).getPassword();
        byte[] bytesCipheredPassword = CipherServiceProvider.stringOfArrayBytesToArrayBytes(stringBytesCipheredPassword);
        String password = AESCipherLibrary.decifrarAES(bytesCipheredPassword, userRegistered.getSecretKey());

        this.inputTestPassword.setText(password);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.inputTestWebSiteTitle.setOnKeyReleased(keyEvent -> {
            List<Result> listResults = GETCustomSearchsServiceProvider.getResults(this.inputTestWebSiteTitle.getText());
            if(listResults != null){
                for(int i = 0; i<listResults.size(); i++){
                    HBox hBoxSearchFound = new HBox();
                    hBoxSearchFound.setAlignment(Pos.CENTER_LEFT);
                    hBoxSearchFound.setPrefWidth(306);
                    hBoxSearchFound.setPrefHeight(73);

                    ImageView imageViewIconWebSite = new ImageView(GETIconWebSiteServiceProvider.getPNGIcon(listResults.get(i).getFormattedUrl(),50));
                    imageViewIconWebSite.setFitWidth(50);
                    imageViewIconWebSite.setFitHeight(50);
                    HBox.setMargin(imageViewIconWebSite,new Insets(20));
                    HBox.setHgrow(imageViewIconWebSite, Priority.ALWAYS);

                    hBoxSearchFound.getChildren().add(imageViewIconWebSite);

                    Label labelWebSiteName = new Label(listResults.get(i).getTitle());
                    labelWebSiteName.setFont(Font.font(14));
                    labelWebSiteName.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

                    HBox.setMargin(labelWebSiteName,new Insets(20));
                    HBox.setHgrow(labelWebSiteName,Priority.ALWAYS);

                    hBoxSearchFound.getChildren().add(labelWebSiteName);

                    hBoxSearchFound.setOnMouseEntered(event -> {
                        hBoxSearchFound.setStyle("-fx-background-color: #525b7c;");
                    });

                    hBoxSearchFound.setOnMouseExited(dragEvent -> {
                        hBoxSearchFound.setStyle("-fx-background-color: #313649;");
                    });

                    hBoxSearchFound.setStyle("-fx-background-color: #313649;");

                    hBoxSearchFound.setOnMouseClicked(event -> {
                        for(int j = 0; j<this.datosSearchsFound.size(); j++){
                            if(datosSearchsFound.get(j).isHover()){
                                ImageView imageIconWebSite = (ImageView) datosSearchsFound.get(j).getChildren().get(0);
                                this.iconWebSite.setImage(imageIconWebSite.getImage());

                                Label labelWebSiteTitle = (Label) datosSearchsFound.get(j).getChildren().get(1);
                                inputTestWebSiteTitle.setText(labelWebSiteTitle.getText());

                                this.inputTestWebSiteURL.setText(listResults.get(j).getFormattedUrl());
                            }
                        }
                        this.listViewSearchsFound.setVisible(false);
                    });

                    this.datosSearchsFound.add(hBoxSearchFound);
                }
            }

            this.listViewSearchsFound.setItems(this.datosSearchsFound);
            this.listViewSearchsFound.setVisible(true);
        });
    }
}
