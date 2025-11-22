package com.juanfran.accountsmanager.daos;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IUserDAOS;
import com.juanfran.accountsmanager.managers.UserManager;
import com.juanfran.accountsmanager.models.UserModel;
import javafx.scene.image.Image;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

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
        for(int i = 0; i<this.usersManager.Users.size(); i++){
            if(this.usersManager.Users.get(i).getName().equals(userName)){
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
        for(int i = 0; i<this.usersManager.Users.size(); i++){
            if(this.usersManager.Users.get(i).getEmail().equals(email)){
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
        this.usersManager.Users.add(newUser);
    }

    /**
     * Este método se encarga de obtener un usuario
     * a través del email que recibe el método
     * @param email
     * @return UserModel
     */
    @Override
    public UserModel getUserByEmail(String email) {
        for(int i = 0; i<this.usersManager.Users.size(); i++){
            if(this.usersManager.Users.get(i).getEmail().equals(email)){
                return this.usersManager.Users.get(i);
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
        for(int i = 0; i<this.usersManager.Users.size(); i++){
            if(this.usersManager.Users.get(i).getName().equals(name)){
                return this.usersManager.Users.get(i);
            }
        }
        return null;
    }

    /**
     * Este método se encarga de eliminar
     * un usuario de la aplicación
     * @param userRegistered
     */
    @Override
    public void removeUser(UserModel userRegistered) {
        this.logger.info("Eliminar el usuario " + userRegistered.getIdUser() + " de la aplicación");

        this.usersManager.deleteUser(" { Call DeleteUser (?) } ",userRegistered.getIdUser());

        this.usersManager.Users.remove(userRegistered);
    }

    /**
     * Este método se encarga de
     * actualizar la foto de perfil
     * de el usuario de la cuenta
     * @param photoProfile
     */
    @Override
    public void updatePhotoProfileUser(Integer idUser, File photoProfile) {
        for(int i = 0; i<this.usersManager.Users.size(); i++){
            if(this.usersManager.Users.get(i).getIdUser()==idUser){
                this.usersManager.updatePhotoProfileUser(" { Call updatePhotoProfileUser (?,?) } ",idUser,photoProfile);
                this.usersManager.Users.get(i).setPhotoUserProfile(new Image(photoProfile.getAbsolutePath()));
            }
        }
    }
}
