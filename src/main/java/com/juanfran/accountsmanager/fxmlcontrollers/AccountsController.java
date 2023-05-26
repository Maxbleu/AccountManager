package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.daos.AccountDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.models.AccountModel;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import de.jensd.fx.glyphs.icons525.Icons525;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
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
    public ListView<AnchorPane> listViewAccounts;

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

    //  CAMPOS
    public final ObservableList<AnchorPane> datosAccounts = FXCollections.observableArrayList();

    //  CONSTRUCTOR PRINCIPAL DE LA CLASE

    public AccountsController(){
        this.accountDAOS = ((AccountDAOS) OrchestratorProyectDependences.getService(AccountDAOS.class));
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    //  MÉTODOS

    /**
     * Este método se encarga de eliminar una
     * cuenta del usuario de la aplicación
     * @param actionEvent
     */
    public void deleteAccount(ActionEvent actionEvent) {
        Optional<ButtonType> action = ViewServiceProvider.launchAlert("Confirmación","¿Desea eliminar esta cuenta?", Alert.AlertType.CONFIRMATION);
        if(action.get()==ButtonType.OK){
            for(int i = 0; i<this.datosAccounts.size(); i++){
                CheckBox checkBox = (CheckBox) this.datosAccounts.get(i).getChildren().get(0);
                if(checkBox.isSelected()){
                    this.datosAccounts.remove(i);
                    this.accountDAOS.removeAccount(this.accountDAOS.getAccounts().get(i));
                }
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
    private void loadDetailAccountPanel(AccountModel account){

        //  PONER ICONO CROSS
        //this.buttonCloseDetailAccountPanel.setGraphic();

        //  IMAGEN DEL ICONO DE LA PÁGINA WEB
        this.imgIconWebSite.setImage(this.imgIconWebSite.getImage());

        //  LABELS
        this.labelPanelWebSiteTitle.setText(account.getWebSiteTitle());

        this.labelPanelUserName.setText(account.getUserName());

        this.labelPanelEmail.setText(account.getEmail());

        this.labelPanelPassword.setText(account.getPasswordModel().getPassword());

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.accountDAOS.getAccounts().size() > 0){
            for(int i = 0; i<this.accountDAOS.getAccounts().size(); i++){
                AnchorPane anchorPaneAccount = new AnchorPane();
                anchorPaneAccount.setPrefWidth(1003);
                anchorPaneAccount.setPrefHeight(50);

                CheckBox checkBoxAnchorPane = new CheckBox();
                checkBoxAnchorPane.setVisible(false);
                checkBoxAnchorPane.setLayoutX(246);
                checkBoxAnchorPane.setLayoutY(34);

                checkBoxAnchorPane.setOnAction(actionEvent -> {
                    if(checkBoxAnchorPane.isSelected()){
                        this.buttonDeleteAccount.setDisable(false);
                    }else{
                        this.buttonDeleteAccount.setDisable(true);
                    }
                });

                anchorPaneAccount.getChildren().add(checkBoxAnchorPane);

                AccountModel account = this.accountDAOS.getAccounts().get(i);

                ImageView iconWebSite = null;
                try
                {
                    //  Hacer que se muestre el icono de la página
                    URL websiteUrl = new URL(account.getWebSiteAddress() + "/favicon.ico");
                    iconWebSite = new ImageView(String.valueOf(websiteUrl));
                }
                catch (IOException e) {
                    this.logger.error(e.getMessage());
                }
                iconWebSite.setFitWidth(97);
                iconWebSite.setFitHeight(85);
                iconWebSite.setLayoutX(105);
                iconWebSite.setLayoutY(9);

                anchorPaneAccount.getChildren().add(iconWebSite);

                Label etiquetaSiteName = new Label();
                etiquetaSiteName.setFont(new Font(16));
                etiquetaSiteName.setLayoutX(300);
                etiquetaSiteName.setLayoutY(34);
                etiquetaSiteName.setText(account.getWebSiteTitle());
                etiquetaSiteName.setStyle("-fx-text-fill: white;");

                anchorPaneAccount.getChildren().add(etiquetaSiteName);

                Label etiquetaEmail = new Label();
                etiquetaEmail.setFont(new Font(16));
                etiquetaEmail.setLayoutX(617);
                etiquetaEmail.setLayoutY(34);
                etiquetaEmail.setText(account.getEmail());
                etiquetaEmail.setStyle("-fx-text-fill: white;");

                anchorPaneAccount.getChildren().add(etiquetaEmail);
                anchorPaneAccount.setStyle("-fx-background-color: #313649;");

                anchorPaneAccount.setOnMouseEntered(event -> {
                    anchorPaneAccount.setStyle("-fx-background-color: #525b7c;");
                    checkBoxAnchorPane.setVisible(true);
                });

                anchorPaneAccount.setOnMouseExited(mouseExit ->{
                    anchorPaneAccount.setStyle("-fx-background-color: #313649;");
                    checkBoxAnchorPane.setVisible(false);
                });

                anchorPaneAccount.setOnMouseClicked(event -> {
                    loadDetailAccountPanel(account);
                });

                this.datosAccounts.add(anchorPaneAccount);
            }
            this.listViewAccounts.setItems(this.datosAccounts);
        }
    }
}
