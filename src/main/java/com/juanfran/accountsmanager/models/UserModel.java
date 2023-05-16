package com.juanfran.accountsmanager.models;

import com.juanfran.accountsmanager.managers.UserManager;

import javax.crypto.SecretKey;

public class UserModel {
    private Integer idUser;
    private String email;
    private String name;
    private Integer idPassword;

    //  Pongo aquí la clave simétrica porque
    //  considero que el usuario es el único
    //  que puede ver su información y por lo
    //  cual debe de ser el único que pueda
    //  acceder a ella
    private SecretKey secretKey;

    public UserModel(Integer idUser, String email, String name, Integer password) {
        this.idUser = idUser;
        this.email = email;
        this.name = name;
        this.idPassword = password;
    }

    public UserModel(String email, String name, Integer password){
        this.email = email;
        this.name = name;
        this.idPassword = password;
    }

    public UserModel(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public UserModel(){}

    public Integer getIdUser() {
        if(this.idUser==null){
            this.idUser = UserManager.Users.isEmpty() ? UserManager.Users.get(UserManager.Users.size()-1).getIdUser()+1 : 1;
        }
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdPassword() {
        return idPassword;
    }

    public void setIdPassword(Integer password) {
        this.idPassword = password;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public SecretKey getSecretKey(){
        return this.secretKey;
    }
}
