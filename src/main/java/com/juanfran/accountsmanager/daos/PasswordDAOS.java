package com.juanfran.accountsmanager.daos;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IPasswordDAOS;
import com.juanfran.accountsmanager.managers.PasswordManager;
import com.juanfran.accountsmanager.models.PasswordModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordDAOS implements IPasswordDAOS {

    //  DEPENDECES

    //  PASSWORDMANAGER lo utilizaremos para acceder tanto a la lista como para realizar acciones sobre la base de datos
    private final PasswordManager passwordsManager;

    //  LOGGER lo utilizaremos para informar sobre todos los procesos que se realicen en la clase
    private final Logger logger;
    @Autowired
    public PasswordDAOS(PasswordManager passwordsManager){
        this.passwordsManager = passwordsManager;
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    /**
     * Este método se encarga de actualizar la contraseña
     * a través del id de esta tanto en la lista como
     * en la base de datos.
     * @param idPassword
     * @param password
     */
    @Override
    public void updatePassword(Integer idPassword, String password) {
        this.logger.info("Actualizamos la contraseña");
        for(int i = 0; i<this.passwordsManager.Passwords.size(); i++){
            if(this.passwordsManager.Passwords.get(i).getIdPassword().equals(idPassword)){
                this.passwordsManager.executeUpdatePassword("{ Call updatePassword (?,?) }",idPassword,password);
                this.passwordsManager.Passwords.get(i).setPassword(password);
            }
        }
    }

    /**
     * Este método se encarga de comprobar en base al id y
     * la contraseña que recibe el método es igual
     * a alguna de las almacenadas en la lista
     * @param idPassword
     * @param password
     * @return
     */
    @Override
    public boolean isTheSamePassword(Integer idPassword, String password) {
        for(int i = 0; i<this.passwordsManager.Passwords.size(); i++){
            if(this.passwordsManager.Passwords.get(i).getIdPassword().equals(idPassword) && this.passwordsManager.Passwords.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    /**
     * Este método se encarga de registrar una nueva contraseña
     * tanto en la lista como en la base de datos
     * @param password
     */
    @Override
    public void registerNewPassword(PasswordModel password) {
        this.logger.info("Registramos una nueva contraseña");
        this.passwordsManager.insertNewPassword("{ Call InsertPassword (?,?) }",password);
        this.passwordsManager.Passwords.add(password);
    }

    /**
     * Este método se encarga de obtener una contraseña
     * a través de su id
     * @param id
     * @return PasswordModel
     */
    @Override
    public PasswordModel getPasswordById(Integer id) {
        for(int i = 0; i< this.passwordsManager.Passwords.size(); i++){
            if(this.passwordsManager.Passwords.get(i).getIdPassword().equals(id)){
                return this.passwordsManager.Passwords.get(i);
            }
        }
        return null;
    }

    /**
     * Este método se encarga de actualizar el
     * código de recuperación de la contraseña
     * @param idPassword
     * @param generatedCode
     */
    @Override
    public void updateRecoverPasswordCode(Integer idPassword, Integer generatedCode){
        this.logger.info("Actualizamos el código de recuperación de la contraseña");
        for(int i = 0; i<this.passwordsManager.Passwords.size(); i++){
            if(this.passwordsManager.Passwords.get(i).getIdPassword().equals(idPassword)){
                this.passwordsManager.executeUpdateRecoverPasswordCode("{ Call updateRecoverPasswordCode (?,?) }",idPassword, generatedCode);
                this.passwordsManager.Passwords.get(i).setRecuperationCode(generatedCode);
            }
        }
    }

    /**
     * Este método se encarga de obtener el código
     * de recuperación de la contraseña
     * a través de su id
     * @param idPassword
     * @return Integer
     */
    @Override
    public Integer getRecoverPasswordCode(Integer idPassword) {
        for(int i = 0; i<this.passwordsManager.Passwords.size(); i++){
            if(this.passwordsManager.Passwords.get(i).getIdPassword().equals(idPassword)){
                return this.passwordsManager.Passwords.get(i).getRecuperationCode();
            }
        }
        return null;
    }

    /**
     * Este método se encarga de eliminar
     * la contraseña de la aplicación
     * @param idPassword
     */
    @Override
    public void removePassword(Integer idPassword) {
        for(int i = 0; i<this.passwordsManager.Passwords.size(); i++){
            if(this.passwordsManager.Passwords.get(i).getIdPassword()==idPassword){
                this.passwordsManager.deletePassword("{ Call DeletePassword (?) }",idPassword);
                this.passwordsManager.Passwords.remove(i);
            }
        }
    }
}
