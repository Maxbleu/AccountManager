package com.juanfran.accountsmanager.managers;

import com.example.SqlServerLibrary.ISqlServerLibrary;
import com.example.SqlServerLibrary.SqlServerLibrary;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.models.PasswordModel;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PasswordManager {
    private final ISqlServerLibrary sqlServerLibrary;
    public final List<PasswordModel> Passwords = new ArrayList<>();
    private final Logger logger;
    public PasswordManager(SqlServerLibrary sqlServerLibrary){
        this.sqlServerLibrary = sqlServerLibrary;
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    /**
     * Este método se encarga de obtener todas
     * las contraseñas de todas las cuentas
     * y usuarios de la aplicación
     * @param query
     */
    public void selectPasswords(String query) {
        try {

            //  Obtenemos el método para pasarselo por parámetro al método
            Method dataHandler = PasswordManager.class.getMethod("dataHandlerPassword", ResultSet.class);

            //  Llamamos al método para obtener todas las cuentas del usuario
            this.sqlServerLibrary.executeSelectStoreProcedure(query,dataHandler,this);
        } catch (NoSuchMethodException e) {

            //  En caso de error se registrará
            logger.error(e.getMessage());
        }
    }

    /**
     * Este método carga todas las contraseñas
     * que recibe de la base de datos
     * @param resultSet
     */
    public void dataHandlerPassword(ResultSet resultSet) {
        try{

            //  Comprobamos si los datos recogidos no son nullos
            if(resultSet != null) {

                //  Creamos un objeto accountModel para almacenar la información de cada cuenta
                while (resultSet.next()) {
                    PasswordModel password = new PasswordModel();

                    password.setIdPassword(resultSet.getInt(1));
                    password.setPassword(resultSet.getString(2));
                    password.setRecuperationCode(resultSet.getInt(3));

                    //  La almacenamos en la lista
                    Passwords.add(password);
                }
            }
        }catch(Exception e){

            //  En caso de error se registrará
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de actualizar
     * el código de recuperación de una contraseña
     * en la base de datos
     * @param query
     * @param generatedCode
     * @param idPassword
     */
    public void executeUpdateRecoverPasswordCode(String query, Integer generatedCode, Integer idPassword) {
        this.sqlServerLibrary.executeUpdateStoreProcedure(query,true,generatedCode,idPassword);
    }

    /**
     * Este método se encarga de insertar un nuevo
     * usuario en la base de datos
     * @param query
     * @param newPassword
     */
    public void insertNewPassword(String query, PasswordModel newPassword) {
        this.sqlServerLibrary.executeUpdateStoreProcedure(query,true, newPassword.getIdPassword(),newPassword.getPassword());
    }

    /**
     * Este método se encarga de actualizar
     * la contraseña tanto de un usuario
     * como de la cuenta de un usuario
     * en la base de datos
     * @param query
     * @param idPassword
     * @param password
     */
    public void executeUpdatePassword(String query, Integer idPassword, String password) {
        this.sqlServerLibrary.executeUpdateStoreProcedure(query, true, idPassword, password);
    }

    /**
     * Este método se encarga de eliminar
     * una contraseña de la base de datos
     * @param query
     * @param idPassword
     */
    public void deletePassword(String query, Integer idPassword){
        this.logger.info("Eliminamos la contraseña " + idPassword + " de la base de datos");
        this.sqlServerLibrary.executeUpdateStoreProcedure(query, true, idPassword);
    }
}
