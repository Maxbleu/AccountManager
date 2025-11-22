package com.juanfran.accountsmanager.interfaces;

import com.juanfran.accountsmanager.models.UserModel;
import javafx.scene.image.Image;

import java.io.File;

public interface IUserDAOS {
    boolean thereUserRegisteredWithSameUserName(String userName);
    boolean thereUserRegisteredWithSameEmail(String email);
    void registerNewUser(UserModel newUser);
    UserModel getUserByEmail(String email);
    UserModel getUserByName(String name);
    void removeUser(UserModel userRegistered);
    void updatePhotoProfileUser(Integer idUser , File photoProfile);
}
