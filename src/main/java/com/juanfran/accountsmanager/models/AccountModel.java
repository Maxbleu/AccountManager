package com.juanfran.accountsmanager.models;

public class AccountModel {
    private Integer idAccount;
    private String webSiteAddress;
    private String userName;
    private String email;
    private PasswordModel password;
    private Integer idUser;

    public AccountModel(Integer idAccount, String webSiteAddress, String userName, String email, PasswordModel password, Integer idUser) {
        this.idAccount = idAccount;
        this.webSiteAddress = webSiteAddress;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.idUser = idUser;
    }

    public AccountModel(String webSiteAddress, String userName, String email, PasswordModel password, Integer idUser) {
        this.webSiteAddress = webSiteAddress;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.idUser = idUser;
    }

    public AccountModel() {}

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public String getWebSiteAddress() {
        return webSiteAddress;
    }

    public void setWebSiteAddress(String webSiteAddress) {
        this.webSiteAddress = webSiteAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PasswordModel getPasswordModel() {
        return password;
    }

    public void setPasswordModel(PasswordModel password) {
        this.password = password;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
