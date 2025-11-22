package com.juanfran.accountsmanager.services;

import com.juanfran.accountsmanager.Main;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;

public final class ViewServiceProvider {

    private ViewServiceProvider() {}

    /**
     * Este método se encarga de proporcionarnos
     * la ruta del fichero de la vista
     * @param nameView
     * @return URL
     */
    private static URL getViewPath(String nameView){
        URL urlPath = null;
        try{

            //  Almacenamos la ruta del fichero de la vista
            urlPath = new URL(Main.class.getResource("Views/"+nameView).toString());
        }catch(Exception e){
            OrchestratorProyectDependences.getLogger().error(e.getMessage());
        }

        //  Devolvemos la ruta de la vista
        return urlPath;
    }

    /**
     * Este método se encarga de proporcionarnos
     * la vista cargada para poder en ciertas
     * ocasiones pasar parámetros al controlador
     * @param nameView
     * @return FXMLLoader
     */
    public static FXMLLoader getFXMLoaderFromView(String nameView) {

        //  Obtenemos la ruta de la vista
        URL urlPath = getViewPath(nameView);

        //  Devolvemos la vista cargada
        return new FXMLLoader(urlPath);
    }

    /**
     * Este método se encarga de cargar la vista
     * y devolvernosla en una escena
     * @param nameView
     * @return
     */
    public static Scene getScene(String nameView){
        Scene newScene = null;
        try{
            OrchestratorProyectDependences.getLogger().info("Cargamos la escena " + nameView);

            //  Obtenemos la vista
            FXMLLoader fxmlLoader = getFXMLoaderFromView(nameView);

            //  Cargamos la vista
            Parent root = fxmlLoader.load();

            //  Guardamos la vista en un escena
            newScene = new Scene(root);
        }catch(Exception e){
            OrchestratorProyectDependences.getLogger().error(e.getMessage());
        }

        //  Devolvemos la escena con la vista cargada
        return newScene;
    }

    /**
     * Este método se encarga de proporcionarnos
     * ventanas con las vistas que pasemos por
     * parámetro al método
     * @param nameView
     * @param titleStage
     * @return
     */
    public static Stage getNewStage(String nameView, String titleStage){

        //  Creamos una ventana
        Stage newStage = new Stage();

        //  Obtenemos una escena con la vista que ha pasado el usuario por parametro
        Scene newScene = getScene(nameView);

        //  Insertamos la escena en la ventana creada anteriormente
        newStage.setScene(newScene);

        //  Indicamos un título para la ventana
        newStage.setTitle(titleStage);

        //  Devolvemos la ventana
        return newStage;
    }

    /**
     * Este método se encarga lanzar alertas en el programa
     * pidiendo el titulo, el contenido y el tipo de alerta
     * de este
     * @param title
     * @param content
     * @param alertType
     */
    public static Optional<ButtonType> launchAlert(String title, String content, Alert.AlertType alertType){
        OrchestratorProyectDependences.getLogger().info("Lanzamos una alerta");

        //  Creamos una alerta indicando en su constructor el tipo de esta
        Alert alertaFormulario = new Alert(alertType);

        //  Indicamos el título de la alerta
        alertaFormulario.setTitle(title);

        //  Indicamos el contenido de la alerta
        alertaFormulario.setContentText(content);

        //  Mostramos la alerta
        Optional<ButtonType> action = alertaFormulario.showAndWait();

        return alertType.equals(Alert.AlertType.CONFIRMATION) ? action : null;
    }
}
