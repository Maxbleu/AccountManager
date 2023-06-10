package com.juanfran.accountsmanager.fxmlcontrollers;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.models.enums.SecurityPassword;
import com.juanfran.accountsmanager.services.GeneratorPasswordServiceProvider;
import de.jensd.fx.glyphs.icons525.Icons525;
import de.jensd.fx.glyphs.icons525.Icons525View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class GeneratorPasswordController implements Initializable {

    //  COMPONENTES GRÁFICOS
    @FXML
    public Slider sliderPasswordLenght;
    @FXML
    public Label labelNumberSlider;
    @FXML
    public ToggleButton toggleSimbolos;
    @FXML
    public ToggleButton toggleDigitos;
    @FXML
    public ToggleButton toggleLetras;
    @FXML
    public Icons525View icoPassword;
    @FXML
    public Label typePassword;
    @FXML
    public Button buttonGeneratePassword;
    @FXML
    public Button buttonCopiar;
    @FXML
    public TextField textFieldPassword;

    //  DEPENDENCES
    private final Logger logger;

    //  CAMPOS

    //  CONSTRUCTOR PRINCIPAL DE LA CLASE
    public GeneratorPasswordController(){
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    //  MÉTODOS

    /**
     * Este método se encarga de valorar la
     * seguridad de la contraseña que recibe
     * por parámetro
     * @param password
     * @return
     */
    private SecurityPassword isSecurityPassword(String password){
        if (password.length() < 8) {
            return SecurityPassword.WEAK;
        }

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecialChar = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();

        if (hasUppercase && hasLowercase && hasDigit && hasSpecialChar) {
            return SecurityPassword.HARD;
        } else {
            return SecurityPassword.STRONG;
        }
    }

    /**
     * Este método se encarga de seleccionar
     * la longitud de aleatoria para una contraseña
     * @return
     */
    private double selectRandomLenght(){
        Random random = new Random();
        Integer max = castDoubleToInteger(this.sliderPasswordLenght.getMax());
        Integer main = castDoubleToInteger(this.sliderPasswordLenght.getMin());
        return random.nextInt(max - main + 1) + main;
    }

    /**
     * Este método se encarga de convertir
     * un valor double a Integer
     * @param number
     * @return
     */
    private Integer castDoubleToInteger(Double number){
        return Integer.valueOf(number.intValue());
    }

    /**
     * Este método se encarga se generar una contraseña
     * en base a las configuraciones que indica el
     * usuario
     * @param actionEvent
     */
    public void generatePassword(ActionEvent actionEvent) {
        String password = GeneratorPasswordServiceProvider.generatePassword(
                this.sliderPasswordLenght.getValue()==0.0 ? selectRandomLenght() : this.sliderPasswordLenght.getValue(),
                this.toggleLetras.isSelected(),this.toggleDigitos.isSelected(),this.toggleSimbolos.isSelected());
        switch (isSecurityPassword(password)){
            case WEAK:
                    this.icoPassword.setFill(Paint.valueOf("red"));
                    this.typePassword.setText("Weak Password");
                    this.typePassword.setStyle("-fx-text-fill: red;");
                break;

            case STRONG:
                    this.icoPassword.setFill(Paint.valueOf("orange"));
                    this.typePassword.setText("Hard Password");
                    this.typePassword.setStyle("-fx-text-fill: orange;");
                break;

            case HARD:
                    this.icoPassword.setFill(Paint.valueOf("green"));
                    this.typePassword.setText("Strong Password");
                    this.typePassword.setStyle("-fx-text-fill: green;");
                break;
        }
        this.logger.info("Nueva contraseña generada: " + password);
        this.textFieldPassword.setText(password);
    }

    /**
     * Este método se encarga de realizar
     * la acción de copiar la contraseña
     * generada
     * @param actionEvent
     */
    public void copiarPassword(ActionEvent actionEvent) {
        this.logger.info("El usuario ha copiado la contraseña " + this.textFieldPassword.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(this.textFieldPassword.getText());
        clipboard.setContents(selection, null);
        this.logger.info("La contraseña ha sido copiada");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Icons525View iconCopy = new Icons525View(Icons525.COPY);
        iconCopy.setSize("17");
        iconCopy.setFill(Paint.valueOf("white"));
        this.buttonCopiar.setGraphic(iconCopy);

        Icons525View iconKey = new Icons525View(Icons525.KEY);
        iconKey.setSize("17");
        iconKey.setFill(Paint.valueOf("white"));
        this.buttonGeneratePassword.setGraphic(iconKey);

        this.sliderPasswordLenght.setOnDragDetected(event -> {
            Double lenghtPassword = this.sliderPasswordLenght.getValue();
            this.labelNumberSlider.setText(String.valueOf(lenghtPassword.intValue()));
        });
        generatePassword(new ActionEvent());
    }
}
