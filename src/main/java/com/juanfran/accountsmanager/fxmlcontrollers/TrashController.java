package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.Main;
import com.juanfran.accountsmanager.daos.AccountDAOS;
import com.juanfran.accountsmanager.daos.PasswordDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.models.AccountModel;
import com.juanfran.accountsmanager.services.GETIconWebSiteServiceProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TrashController implements Initializable {

    //  COMPONENTES GRÁFICOS
    @FXML
    public ListView listViewRemovedAccounts;
    @FXML
    public Button buttonEmptyTrash;
    @FXML
    public Button buttonDeleteAccount;

    //  DEPENDENCES

    private final Logger logger;
    private final AccountDAOS accountDAOS;
    private final PasswordDAOS passwordDAOS;

    //  CAMPOS

    public final ObservableList<HBox> datosDeletedAccounts = FXCollections.observableArrayList();
    public static List<AccountModel> removedAccounts;

    // CONSTRUCTOR PRINCIPAL DE LA CLASE

    public TrashController(){
        this.logger = OrchestratorProyectDependences.getLogger();
        this.accountDAOS = (AccountDAOS) OrchestratorProyectDependences.getService(AccountDAOS.class);
        this.passwordDAOS = (PasswordDAOS) OrchestratorProyectDependences.getService(PasswordDAOS.class);
        removedAccounts = this.accountDAOS.getListAccountsRemoved();
    }

    //  MÉTODOS

    /**
     * Este método se encarga de eliminar
     * todas las cuentas removidas. De la
     * aplicación y la base de datos
     * @param actionEvent
     */
    public void emptyTrash(ActionEvent actionEvent) {
        if(this.datosDeletedAccounts.size()>0){
            for(int i = 0; i<this.datosDeletedAccounts.size(); i++){
                this.datosDeletedAccounts.remove(i);
                this.passwordDAOS.removePassword(removedAccounts.get(i).getIdPassword());
                this.accountDAOS.removeAccount(removedAccounts.get(i));
            }
            this.logger.info("La papelera ha sido vaciada");
        }
    }

    /**
     * Este método se encarga de obtener la
     * cuentas seleccionadas
     * @return
     */
    private List<HBox> getCheckedAccounts(){
        List<HBox> accountsChecked = new ArrayList<>();
        for(int i = 0; i<this.datosDeletedAccounts.size(); i++){
            CheckBox checkBox = (CheckBox) this.datosDeletedAccounts.get(i).getChildren().get(0);
            if(checkBox.isSelected()){
                accountsChecked.add(this.datosDeletedAccounts.get(i));
            }
        }
        return accountsChecked;
    }

    /**
     * Este método se encarga de eliminar
     * una cuenta personal de la aplicación
     * @param actionEvent
     */
    public void deleteAccounts(ActionEvent actionEvent) {
        List<HBox> accountsChecked = getCheckedAccounts();
        for(int i = 0; i<accountsChecked.size(); i++){
            for(int j = 0; j<datosDeletedAccounts.size(); j++){
                if(accountsChecked.get(i)==datosDeletedAccounts.get(j)){
                    this.passwordDAOS.removePassword(removedAccounts.get(i).getIdPassword());
                    this.accountDAOS.removeAccount(removedAccounts.get(i));
                    this.logger.info("Hemos eliminada de la aplicación la cuenta " + removedAccounts.get(i).getIdAccount());
                }
            }
            this.datosDeletedAccounts.remove(accountsChecked.get(i));
        }
        this.buttonDeleteAccount.setDisable(true);
    }

    /**
     * Este método se encarga de recuperar
     * una cuenta personal removida
     * @param actionEvent
     */
    public void restoreAccount(ActionEvent actionEvent){
        for(int i = 0; i<this.datosDeletedAccounts.size(); i++){
            if(this.datosDeletedAccounts.get(i).isHover()){
                this.datosDeletedAccounts.remove(i);
                this.accountDAOS.updateIsRemoved(removedAccounts.get(i).getIdAccount(),false);
                removedAccounts.remove(i);
                this.logger.info("La cuenta " + removedAccounts.get(i).getIdAccount() + " ha sido recuperada");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(this.removedAccounts.size() > 0){
            for(int i = 0; i<this.removedAccounts.size(); i++){
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
                etiquetaSiteName.setStyle("-fx-text-fill: white;");

                anchorPaneAccount.getChildren().add(etiquetaSiteName);

                Label etiquetaEmail = new Label();
                etiquetaEmail.setFont(new Font(16));
                etiquetaEmail.setLayoutX(400);
                etiquetaEmail.setLayoutY(25);
                etiquetaEmail.setText(account.getEmail());
                etiquetaEmail.setStyle("-fx-text-fill: white;");

                anchorPaneAccount.getChildren().add(etiquetaEmail);

                Button buttonRestoreAccount = new Button();

                ImageView iconNoDelete = new ImageView(Main.class.getResource("Images/iconNoEliminar.png").toString());
                iconNoDelete.setFitWidth(24);
                iconNoDelete.setFitHeight(24);

                buttonRestoreAccount.setGraphic(iconNoDelete);

                buttonRestoreAccount.getStyleClass().add("buttonRestoreAccountTrashView");
                buttonRestoreAccount.setLayoutX(1000);
                buttonRestoreAccount.setLayoutY(24);

                buttonRestoreAccount.setPrefHeight(23);
                buttonRestoreAccount.setPrefWidth(23);

                buttonRestoreAccount.setVisible(false);

                buttonRestoreAccount.setOnAction(actionEvent -> {
                    restoreAccount(actionEvent);
                });

                AnchorPane.setRightAnchor(buttonRestoreAccount,new Double(110));

                anchorPaneAccount.getChildren().add(buttonRestoreAccount);

                anchorPaneAccount.setStyle("-fx-background-color: #313649;");

                hBoxAccount.setOnMouseEntered(event -> {
                    checkBoxAnchorPane.setVisible(true);
                    anchorPaneAccount.setStyle("-fx-cursor: hand; -fx-background-color: #525b7c; -fx-transition-delay: 0.5s;");
                });

                hBoxAccount.setOnMouseExited(mouseExit ->{
                    anchorPaneAccount.setStyle("-fx-background-color: #313649;");
                    checkBoxAnchorPane.setVisible(false);
                });

                anchorPaneAccount.setOnMouseEntered(event -> {
                    anchorPaneAccount.setStyle("-fx-background-color: #525b7c; -fx-cursor: hand;");
                    checkBoxAnchorPane.setVisible(true);
                    buttonRestoreAccount.setVisible(true);
                });

                anchorPaneAccount.setOnMouseExited(mouseExit ->{
                    anchorPaneAccount.setStyle("-fx-background-color: #313649;");
                    checkBoxAnchorPane.setVisible(false);
                    buttonRestoreAccount.setVisible(false);
                });

                anchorPaneAccount.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(anchorPaneAccount, Priority.ALWAYS);
                hBoxAccount.getChildren().add(anchorPaneAccount);

                this.datosDeletedAccounts.add(hBoxAccount);
            }
            this.listViewRemovedAccounts.setItems(this.datosDeletedAccounts);
        }
    }
}
