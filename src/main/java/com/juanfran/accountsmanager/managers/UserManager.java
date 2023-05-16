package com.juanfran.accountsmanager.managers;

import com.example.SqlServerLibrary.ISqlServerLibrary;
import com.example.SqlServerLibrary.SqlServerLibrary;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.models.UserModel;
import com.juanfran.accountsmanager.services.CipherServiceProvider;
import org.apache.log4j.Logger;
import org.cipherLibrary.Ciphers.AESCipherLibrary;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private final ISqlServerLibrary sqlServerLibrary;
    public static final List<UserModel> Users = new ArrayList<>();
    private final Logger logger;
    public UserManager(SqlServerLibrary sqlServerLibrary){
        this.sqlServerLibrary = sqlServerLibrary;
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    /**
     * Este método se encarga de obtener todos los usuarios
     * registrados en la aplicación hasta ahora gracias
     * a la sentencia sql que recibe el método
     * @param nameStoreProcedure
     */
    public void selectUsers(String nameStoreProcedure) {
        try {

            //  Obtenemos el método para pasarselo por parámetro al método
            Method dataHandlerUser = UserManager.class.getDeclaredMethod("dataHandlerUser", ResultSet.class);

            //  Llamamos al método executeSelectStoreProcedure para obtener todos los usuarios
            this.sqlServerLibrary.executeSelectStoreProcedure(nameStoreProcedure, dataHandlerUser,this);
        } catch (NoSuchMethodException e) {

            //  En caso de error se registrará
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método carga todos los usuarios de la
     * aplicación que recibe por parámetro de
     * la base de datos
     * @param resultSet
     */
    public void dataHandlerUser(ResultSet resultSet) {
        try{

            //  Comprobamos si los datos recogidos no son nullos
            if(resultSet != null) {

                //  Mientras que no hayan datos en el siguiente registro el bucle continuará
                while (resultSet.next()) {

                    //  Creamos un objeto UserModel para almacenar la información de cada usuario
                    UserModel user = new UserModel();

                    user.setIdUser(resultSet.getInt(1));
                    user.setSecretKey(CipherServiceProvider.generateAsemetricKey(user.getIdUser().toString()));

                    user.setEmail(AESCipherLibrary.decifrarAES(resultSet.getBytes(2),user.getSecretKey()));

                    user.setName(resultSet.getString(3));

                    user.setIdPassword(Integer.parseInt(AESCipherLibrary.decifrarAES(resultSet.getBytes(4),user.getSecretKey())));

                    //  La almacenamos en la lista
                    Users.add(user);
                }
            }
        }catch(Exception e){

            //  En caso de error se registrará
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de insertar un nuevo
     * usuario en la base de datos gracias
     * a la sentencia sql que recibe por parámetro
     * con los parámetros de esta
     * @param query
     * @param user
     */
    public void insertUser(String query, UserModel user) {
        byte[] cipheredEmailUser = AESCipherLibrary.cifrarAES(user.getEmail(),user.getSecretKey());
        byte[] cipheredIdPasswordUser = AESCipherLibrary.cifrarAES(user.getIdPassword().toString(),user.getSecretKey());
        this.sqlServerLibrary.executeUpdateStoreProcedure(query, true, cipheredEmailUser,user.getName(),cipheredIdPasswordUser);
    }
}
