package com.juanfran.accountsmanager.managers;

import com.example.SqlServerLibrary.ISqlServerLibrary;
import com.example.SqlServerLibrary.SqlServerLibrary;
import com.juanfran.accountsmanager.daos.PasswordDAOS;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IPasswordDAOS;
import com.juanfran.accountsmanager.models.AccountModel;
import com.juanfran.accountsmanager.models.UserModel;
import org.apache.log4j.Logger;
import org.cipherLibrary.Ciphers.AESCipherLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountManager {
    private final ISqlServerLibrary sqlServerLibrary;
    public final List<AccountModel> Accounts = new ArrayList<>();
    private final PasswordDAOS passwordDAOS;
    public static UserModel userRegistered;
    private final Logger logger;

    @Autowired
    public AccountManager(SqlServerLibrary sqlServerLibrary, PasswordDAOS passwordDAOS) {
        this.sqlServerLibrary = sqlServerLibrary;
        this.passwordDAOS = passwordDAOS;
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    /**
     * Este método se encarga de obtener
     * todas las cuentas de un usuario
     * de la aplicación
     *
     * @param query
     */
    public void selectAccountsByUserId(String query, Integer userId) {
        try {

            //  Ciframos el parámetro userId para poder compararlo con los almacenados en la base de datos
            byte[] cipheredUserId = AESCipherLibrary.cifrarAES(userId.toString(), userRegistered.getSecretKey());

            //  Obtenemos el método para pasarselo por parámetro al método
            Method dataHandler = AccountManager.class.getMethod("dataHandlerAccount", ResultSet.class);

            //  Llamamos al método para obtener todas las cuentas del usuario
            this.sqlServerLibrary.executeSelectStoreProcedure(query, dataHandler, this, cipheredUserId);
        } catch (NoSuchMethodException e) {

            //  En caso de error se registrará
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de insertar
     * una cuenta en la base de datos
     *
     * @param query
     * @param account
     */
    public void insertAccount(String query, AccountModel account) {
        account.setIdUser(userRegistered.getIdUser());
        byte[] cipheredEmailAccount = AESCipherLibrary.cifrarAES(account.getEmail(), userRegistered.getSecretKey());
        byte[] cipheredPasswordIdAccount = AESCipherLibrary.cifrarAES(account.getIdPassword().toString(), userRegistered.getSecretKey());
        byte[] cipheredUserIdAccount = AESCipherLibrary.cifrarAES(account.getIdUser().toString(), userRegistered.getSecretKey());
        this.sqlServerLibrary.executeUpdateStoreProcedure(query, true, account.getIdAccount(),account.getWebSiteAddress(), account.getWebSiteTitle(), account.getUserName(), cipheredEmailAccount, cipheredPasswordIdAccount, cipheredUserIdAccount);
    }

    /**
     * Este método se encarga de cargar todas
     * las cuentas que recibe por parámetro
     * de la base de datos a la lista de
     * cuentas del usuario de la aplicación
     *
     * @param resultSet
     */
    public void dataHandlerAccount(ResultSet resultSet) {
        try {

            //  Comprobamos si los datos recogidos no son nullos
            if (resultSet != null) {

                //  Mientras que no hayan datos en el siguiente registro el bucle continuará
                while (resultSet.next()) {

                    //  Creamos un objeto accountModel para almacenar la información de cada cuenta
                    AccountModel account = new AccountModel();

                    account.setIdAccount(resultSet.getInt(1));
                    account.setWebSiteAddress(resultSet.getString(2));
                    account.setUserName(resultSet.getString(3));
                    account.setEmail(AESCipherLibrary.decifrarAES(resultSet.getBytes(4), userRegistered.getSecretKey()));
                    account.setIdPassword(Integer.parseInt(AESCipherLibrary.decifrarAES(resultSet.getBytes(5), userRegistered.getSecretKey())));
                    account.setIdUser(Integer.parseInt(AESCipherLibrary.decifrarAES(resultSet.getBytes(6), userRegistered.getSecretKey())));
                    account.setWebSiteTitle(resultSet.getString(7));
                    account.setRemoved(resultSet.getInt(8)==1 ? true : false);

                    //  La almacenamos en la lista
                    Accounts.add(account);
                }
            }
        } catch (Exception e) {

            //  En caso de error se registrará
            this.logger.error(e.getMessage());
        }
    }

    /**
     * Este método se encarga de
     * eliminar una cuenta de la
     * base de datos
     * @param query
     * @param idAccount
     */
    public void deleteAccount(String query, Integer idAccount){

        this.logger.info("Borramos la cuenta " + idAccount + " de la base de datos");

        this.sqlServerLibrary.executeUpdateStoreProcedure(query,true, idAccount);
    }

    /**
     * Este método se encarga de actualizar
     * los datos de la cuenta personal
     * de un usuario en la base de datos
     * @param idAccount
     * @param webSiteAddress
     * @param userName
     * @param email
     * @param webSiteTitle
     */
    public void updateAccount(String query, Integer idAccount, String webSiteAddress, String userName, String email, String webSiteTitle){
        byte[] cipheredEmailAccount = AESCipherLibrary.cifrarAES(email,userRegistered.getSecretKey());
        this.sqlServerLibrary.executeUpdateStoreProcedure(query,true,idAccount,webSiteAddress,userName,cipheredEmailAccount,webSiteTitle);
    }

    /**
     * Este método se encarga de
     * actualizar el campo isRemoved de la tabla
     * Accounts cuando eliminamos
     * una cuenta personal de la vista Accounts.
     * Esto lo hacemos principalmente para indicar
     * que cuenta personal ha sido eliminada
     * de esa vista pero no de la aplicación
     * @param query
     * @param isRemoved
     * @param idAccount
     */
    public void updateIsRemoved(String query, Integer idAccount, boolean isRemoved){
        this.sqlServerLibrary.executeUpdateStoreProcedure(query,true, idAccount,isRemoved ? 1 : 0);
    }
}