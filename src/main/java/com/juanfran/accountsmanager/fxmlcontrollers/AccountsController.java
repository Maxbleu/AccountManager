package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.daos.AccountDAOS;
import com.juanfran.accountsmanager.daos.PasswordDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.models.AccountModel;
import com.juanfran.accountsmanager.models.UserModel;
import com.juanfran.accountsmanager.services.CipherServiceProvider;
import com.juanfran.accountsmanager.services.GETIconWebSiteServiceProvider;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import de.jensd.fx.glyphs.icons525.Icons525;
import de.jensd.fx.glyphs.icons525.Icons525View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.apache.log4j.Logger;
import org.cipherLibrary.Ciphers.AESCipherLibrary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {

    //  COMPONENTES GRÁFICOS

    //  COMPONENTES DE LA VISTA PRINCIPAL
    @FXML
    public Button buttonAddAccount;
    @FXML
    public Button buttonDeleteAccount;
    @FXML
    public ListView<HBox> listViewAccounts;

    //  COMPONENTES DEL PANEL DE LA VISTA DETALLE DE UNA CUENTA PERSONAL
    @FXML
    public Pane panelDetalleAccount;
    @FXML
    public Pane panelLabels;
    @FXML
    public Button buttonCloseDetailAccountPanel;
    @FXML
    public Button buttonEditAccount;
    @FXML
    public ImageView imgIconWebSite;
    @FXML
    public Label labelPanelWebSiteTitle;
    @FXML
    public Label labelPanelUserName;
    @FXML
    public Label labelPanelEmail;
    @FXML
    public Label labelPanelPassword;
    @FXML
    public Label labelPanelWebSiteAddress;

    //  DEPENDENCES
    private final Logger logger;
    private final AccountDAOS accountDAOS;
    private final PasswordDAOS passwordDAOS;

    //  CAMPOS
    public final ObservableList<HBox> datosAccounts = FXCollections.observableArrayList();
    public static UserModel userRegistered;
    private AccountModel accountSelected;

    //  CONSTRUCTOR PRINCIPAL DE LA CLASE
    public AccountsController(){
        this.accountDAOS = ((AccountDAOS) OrchestratorProyectDependences.getService(AccountDAOS.class));
        this.passwordDAOS = ((PasswordDAOS) OrchestratorProyectDependences.getService(PasswordDAOS.class));
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    //  MÉTODOS

    private List<HBox> getCheckedAccounts(){
        List<HBox> accountsChecked = new ArrayList<>();
        for(int i = 0; i<this.datosAccounts.size(); i++){
            CheckBox checkBox = (CheckBox) this.datosAccounts.get(i).getChildren().get(0);
            if(checkBox.isSelected()){
                accountsChecked.add(this.datosAccounts.get(i));
            }
        }
        return accountsChecked;
    }

    /**
     * Este método se encarga de eliminar una
     * cuenta del usuario de la aplicación
     * @param actionEvent
     */
    public void deleteAccount(ActionEvent actionEvent) {
        Optional<ButtonType> action = ViewServiceProvider.launchAlert("Confirmación","¿Desea eliminar esta cuenta?", Alert.AlertType.CONFIRMATION);
        if(action.get()==ButtonType.OK){
            List<HBox> listAccountsChecked = getCheckedAccounts();
            for(int i = 0; i<listAccountsChecked.size(); i++){
                this.datosAccounts.remove(listAccountsChecked.get(i));
                this.accountDAOS.updateIsRemoved(this.accountDAOS.getAccounts().get(i).getIdAccount(),true);
            }
            this.buttonDeleteAccount.setDisable(true);
        }
    }

    /**
     * Este método se utiliza para navegar
     * a la vista detalle cuenta personal
     * para añadir una
     * @param actionEvent
     */
    public void addAccount(ActionEvent actionEvent) {
        try {
            ((BorderPane) this.buttonAddAccount.getScene().getRoot()).setCenter(ViewServiceProvider.getFXMLoaderFromView("AddAndModifyAccountView.fxml").load());
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de cargar
     * los datos de la cuenta personal
     * seleccionada
     * @param account
     */
    private void loadDetailAccountPanel(AccountModel account, Image iconWebSite){

        //  PONER ICONO CROSS
        Icons525View iconCross = new Icons525View(Icons525.CANCEL);
        iconCross.setSize("20");
        iconCross.setFill(Paint.valueOf("white"));

        this.buttonCloseDetailAccountPanel.setGraphic(iconCross);

        //  IMAGEN DEL ICONO DE LA PÁGINA WEB
        this.imgIconWebSite.setImage(iconWebSite);

        //  LABELS
        this.labelPanelWebSiteTitle.setText(account.getWebSiteTitle());

        this.labelPanelUserName.setText(account.getUserName());

        this.labelPanelEmail.setText(account.getEmail());

        String cipheredPassword = this.passwordDAOS.getPasswordById(account.getIdPassword()).getPassword();
        byte[] arrayByteCipheredPassword = CipherServiceProvider.stringOfArrayBytesToArrayBytes(cipheredPassword);
        String password = AESCipherLibrary.decifrarAES(arrayByteCipheredPassword,userRegistered.getSecretKey());

        char[] charPassword = password.toCharArray();
        for(int i = 0; i<charPassword.length; i++) {
            charPassword[i] = '*';
        }

        this.labelPanelPassword.setText(new String(charPassword));

        this.labelPanelWebSiteAddress.setText(account.getWebSiteAddress());

        this.panelDetalleAccount.setVisible(true);
    }

    //  MÉTODOS PANEL DETALLE CUENTA PERSONAL

    /**
     * Este método se encarga de
     * cerrar un panel de vista
     * detalle de una cuenta
     * @param actionEvent
     */
    public void closeDetailAccountPanel(ActionEvent actionEvent) {
        this.panelDetalleAccount.setVisible(false);
    }

    /**
     * Este método se encarga de navegar
     * a la vista addandmodifyAccount
     * para modificar la cuenta seleccionada
     * @param actionEvent
     */
    public void editAccount(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = ViewServiceProvider.getFXMLoaderFromView("AddAndModifyAccountView.fxml");
            Parent root = fxmlLoader.load();
            AddAndModifyAccountController controller = fxmlLoader.getController();

            controller.navigateToAccountViewToModifyAccount(accountSelected, this.imgIconWebSite.getImage());

            ((BorderPane) this.buttonAddAccount.getScene().getRoot()).setCenter(root);
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.listViewAccounts.setStyle("-fx-border-top: 1px solid rgb(106, 117, 154);");
        if(this.accountDAOS.getAccounts().size() > 0){
            for(int i = 0; i<this.accountDAOS.getAccounts().size(); i++){
                if(!this.accountDAOS.getAccounts().get(i).isRemoved()){
                    HBox hBoxAccount = new HBox();
                    hBoxAccount.setAlignment(Pos.CENTER_LEFT);
                    hBoxAccount.setPrefHeight(75);
                    hBoxAccount.setPrefWidth(1104);

                    CheckBox checkBoxAnchorPane = new CheckBox();
                    checkBoxAnchorPane.setVisible(false);

                    checkBoxAnchorPane.setOnAction(actionEvent -> {
                        if(checkBoxAnchorPane.isSelected()){
                            this.buttonDeleteAccount.setDisable(false);
                        }else{
                            this.buttonDeleteAccount.setDisable(true);
                        }
                    });

                    HBox.setMargin(checkBoxAnchorPane,new Insets(0,20,0,0));

                    hBoxAccount.getChildren().add(checkBoxAnchorPane);

                    AnchorPane anchorPaneAccount = new AnchorPane();
                    anchorPaneAccount.setPrefWidth(1080);
                    anchorPaneAccount.setPrefHeight(75);

                    AccountModel account = this.accountDAOS.getAccounts().get(i);

                    String webSiteTitleLowerCase = account.getWebSiteAddress();
                    ImageView iconWebSite = new ImageView(GETIconWebSiteServiceProvider.getPNGIcon(webSiteTitleLowerCase,45));
                    iconWebSite.setFitHeight(40);
                    iconWebSite.setFitWidth(40);
                    iconWebSite.setLayoutX(35);
                    iconWebSite.setLayoutY(18);

                    anchorPaneAccount.getChildren().add(iconWebSite);

                    Label etiquetaSiteName = new Label();
                    etiquetaSiteName.setFont(new Font(16));
                    etiquetaSiteName.setLayoutX(85);
                    etiquetaSiteName.setLayoutY(25);
                    etiquetaSiteName.setText(account.getWebSiteTitle());
                    etiquetaSiteName.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

                    anchorPaneAccount.getChildren().add(etiquetaSiteName);

                    Label etiquetaEmail = new Label();
                    etiquetaEmail.setFont(new Font(16));
                    etiquetaEmail.setLayoutX(400);
                    etiquetaEmail.setLayoutY(25);
                    etiquetaEmail.setText(account.getEmail());
                    etiquetaEmail.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

                    anchorPaneAccount.getChildren().add(etiquetaEmail);
                    anchorPaneAccount.setStyle("-fx-background-color: #313649;");

                    hBoxAccount.setOnMouseEntered(event -> {
                        checkBoxAnchorPane.setVisible(true);
                        anchorPaneAccount.setStyle("-fx-cursor: hand; -fx-background-color: #525b7c; -fx-transition-delay: 0.5s;");
                    });

                    hBoxAccount.setOnMouseExited(mouseExit ->{
                        anchorPaneAccount.setStyle("-fx-background-color: #313649;");
                        checkBoxAnchorPane.setVisible(false);
                    });

                    anchorPaneAccount.setOnMouseClicked(event -> {
                        this.accountSelected = account;
                        loadDetailAccountPanel(account, iconWebSite.getImage());
                    });

                    anchorPaneAccount.setMaxWidth(Double.MAX_VALUE);

                    HBox.setHgrow(anchorPaneAccount, Priority.ALWAYS);
                    hBoxAccount.getChildren().add(anchorPaneAccount);
                    this.datosAccounts.add(hBoxAccount);
                }
            }
            this.listViewAccounts.setItems(this.datosAccounts);
        }
    }
}
