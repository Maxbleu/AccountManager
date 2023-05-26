package com.juanfran.accountsmanager.daos;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IUserDAOS;
import com.juanfran.accountsmanager.managers.UserManager;
import com.juanfran.accountsmanager.models.UserModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAOS implements IUserDAOS {
    private final UserManager usersManager;
    private final Logger logger;
    @Autowired
    public UserDAOS(UserManager usersManager){
        this.usersManager = usersManager;
        this.logger = OrchestratorProyectDependences.getLogger();

    }

    /**
     * Este método se encarga de comprobar si hay un usuario registrado
     * en la aplicación con el mismo nombre de usuario
     * @param userName
     * @return
     */
    @Override
    public boolean thereUserRegisteredWithSameUserName(String userName) {
        for(int i = 0; i<UserManager.Users.size(); i++){
            if(UserManager.Users.get(i).getName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Este método se encarga de comprobar si hay un usuario
     * registrado en la aplicación con el email que recibe el método
     * @param email
     * @return boolean
     */
    @Override
    public boolean thereUserRegisteredWithSameEmail(String email) {
        for(int i = 0; i<UserManager.Users.size(); i++){
            if(UserManager.Users.get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    /**
     * Este método se encarga de registrar
     * nuevos usuarios en la lista de usuarios
     * @param newUser
     */
    @Override
    public void registerNewUser(UserModel newUser) {
        this.logger.info("Registramos un nuevo usuario");
        this.usersManager.insertUser(" { Call InsertUser (?,?,?,?) } ",newUser);
        UserManager.Users.add(newUser);
    }

    /**
     * Este método se encarga de obtener un usuario
     * a través del email que recibe el método
     * @param email
     * @return UserModel
     */
    @Override
    public UserModel getUserByEmail(String email) {
        for(int i = 0; i<UserManager.Users.size(); i++){
            if(UserManager.Users.get(i).getEmail().equals(email)){
                return UserManager.Users.get(i);
            }
        }
        return null;
    }

    /**
     * Este método se encarga de obtener un usuario
     * por el email que recibe el método
     * @param name
     * @return
     */
    @Override
    public UserModel getUserByName(String name) {
        for(int i = 0; i<UserManager.Users.size(); i++){
            if(UserManager.Users.get(i).getName().equals(name)){
                return UserManager.Users.get(i);
            }
        }
        return null;
    }
}
