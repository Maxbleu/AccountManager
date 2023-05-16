package com.juanfran.accountsmanager.interfaces;

import com.juanfran.accountsmanager.models.UserModel;

public interface IUserDAOS {
    boolean thereUserRegisteredWithSameUserName(String userName);
    boolean thereUserRegisteredWithSameEmail(String email);
    void registerNewUser(UserModel newUser);
    UserModel getUserByEmail(String email);
    UserModel getUserByName(String name);
}
