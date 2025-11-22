package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.Main;
import com.juanfran.accountsmanager.daos.UserDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.models.UserModel;
import com.juanfran.accountsmanager.services.CipherServiceProvider;
import com.juanfran.accountsmanager.services.ViewServiceProvider;
import de.jensd.fx.glyphs.icons525.Icons525;
import de.jensd.fx.glyphs.icons525.Icons525View;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ResourceBundle;

public class MainController extends Component implements Initializable {

    //  COMPONENTES GRÁFICOS
    @FXML
    public Button buttonAccounts;
    @FXML
    public Button buttonPasswordGenerator;
    @FXML
    public Button buttonTrash;
    @FXML
    public Button buttonDashboard;
    @FXML
    public VBox vBoxUserProfile;
    @FXML
    public Circle circleElement;
    @FXML
    public Label labelUserName;
    @FXML
    public Button buttonSettings;


    //  DEPENDENCES

    //  Logger lo utilizamos para informar al usuario de lo que ocurre en la aplicación
    private final Logger logger;
    private final UserDAOS userDAOS;

    //  CAMPOS
    public static UserModel userRegistered;

    //  CONSTRUCTOR PRINCIPAL DE LA CLASE
    public MainController(){
        this.logger = OrchestratorProyectDependences.getLogger();
        this.userDAOS = (UserDAOS) OrchestratorProyectDependences.getService(UserDAOS.class);
    }

    //  MÉTODOS

    /**
     * Este método se encarga de hacer navegar a la aplicación
     * de la vista main a la vista Accounts
     * @param actionEvent
     */
    public void navigateToAccountsView(ActionEvent actionEvent) {
        try {
            ((BorderPane) this.buttonAccounts.getScene().getRoot()).setCenter(ViewServiceProvider.getFXMLoaderFromView("AccountsView.fxml").load());
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de hacer navegar
     * a la vista dashboard de la aplicación
     * @param actionEvent
     */
    public void navigateToDashBoard(ActionEvent actionEvent) {
        try {
            ((BorderPane) this.buttonAccounts.getScene().getRoot()).setCenter(ViewServiceProvider.getFXMLoaderFromView("DashBoardView.fxml").load());
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de navegar
     * desde la vista home de la aplicación
     * a la vista Trash
     * @param actionEvent
     */
    public void navigateToTrashView(ActionEvent actionEvent) {
        try {
            ((BorderPane) this.buttonAccounts.getScene().getRoot()).setCenter(ViewServiceProvider.getFXMLoaderFromView("TrashView.fxml").load());
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de navegar
     * desde la vista home de la aplicación
     * a la vista generadora de contraseñas
     * @param actionEvent
     */
    public void navigateToPasswordViewGenerator(ActionEvent actionEvent) {
        try {
            ((BorderPane) this.buttonAccounts.getScene().getRoot()).setCenter(ViewServiceProvider.getFXMLoaderFromView("GeneratorPasswordView.fxml").load());
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de navegar a la
     * vista ajustes de la aplicación
     * @param actionEvent
     */
    public void navigationToSettingsView(ActionEvent actionEvent) {
        try {
            ((BorderPane) this.buttonAccounts.getScene().getRoot()).setCenter(ViewServiceProvider.getFXMLoaderFromView("SettingsView.fxml").load());
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
    }

    /**
     * This method returns JFileChooser with Windows look instead of native java
     * @param chooser
     */
    public static JFileChooser windowsJFileChooser(JFileChooser chooser){
        LookAndFeel previousLF = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            chooser = new JFileChooser();
            UIManager.setLookAndFeel(previousLF);
        } catch (IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException e) {}
        return chooser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImagePattern userProfilePhoto = new ImagePattern(userRegistered.getPhotoUserProfile());
        this.circleElement.setFill(userProfilePhoto);

        this.circleElement.setOnMouseClicked(event -> {
            JFileChooser jf = windowsJFileChooser(new JFileChooser());
            jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jf.setMultiSelectionEnabled(false);
            FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                    "Archivos de imagen", "jpg", "jpeg", "png", "gif","png");
            jf.setFileFilter(imageFilter);
            jf.showOpenDialog(this);
            File seleccion_ruta =jf.getSelectedFile();
            if (seleccion_ruta!= null) {
                ImagePattern imagePattern = new ImagePattern(new Image(seleccion_ruta.getAbsolutePath()));
                this.userDAOS.updatePhotoProfileUser(userRegistered.getIdUser(), seleccion_ruta);
                this.circleElement.setFill(imagePattern);
            }
        });

        this.labelUserName.setText(userRegistered.getName());

        Icons525View iconHome = new Icons525View(Icons525.HOME);
        iconHome.setSize("23");
        iconHome.setFill(Paint.valueOf("white"));

        this.buttonDashboard.setGraphic(iconHome);

        Icons525View iconUser = new Icons525View(Icons525.USER);
        iconUser.setSize("23");
        iconUser.setFill(Paint.valueOf("white"));

        this.buttonAccounts.setGraphic(iconUser);

        Icons525View iconTrash = new Icons525View(Icons525.BIN);
        iconTrash.setSize("23");
        iconTrash.setFill(Paint.valueOf("white"));

        this.buttonTrash.setGraphic(iconTrash);

        Icons525View iconKey = new Icons525View(Icons525.KEY);
        iconKey.setSize("23");
        iconKey.setFill(Paint.valueOf("white"));

        this.buttonPasswordGenerator.setGraphic(iconKey);

        Icons525View iconLeaveApp = new Icons525View(Icons525.WP_COG_O);
        iconLeaveApp.setSize("23");
        iconLeaveApp.setFill(Paint.valueOf("white"));

        this.buttonSettings.setGraphic(iconLeaveApp);
    }

}