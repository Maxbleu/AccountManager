package com.juanfran.accountsmanager.models;

import com.juanfran.accountsmanager.managers.AccountManager;

public class AccountModel {
    private Integer idAccount;
    private String webSiteTitle;
    private String webSiteAddress;
    private String userName;
    private String email;
    private PasswordModel password;
    private Integer idUser;

    public AccountModel(Integer idAccount, String webSiteAddress, String webSiteTitle, String userName, String email, PasswordModel password, Integer idUser) {
        this.idAccount = idAccount;
        this.webSiteAddress = webSiteAddress;
        this.webSiteTitle = webSiteTitle;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.idUser = idUser;
    }

    public AccountModel(String webSiteAddress, String webSiteTitle, String userName, String email, PasswordModel password) {
        this.webSiteAddress = webSiteAddress;
        this.webSiteTitle = webSiteTitle;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public AccountModel() {}

    public Integer getIdAccount() {
        if(this.idAccount == null){
            if(AccountManager.Accounts.size() > 0){
                Integer lastPosition = AccountManager.Accounts.size()-1;
                this.idAccount = AccountManager.Accounts.get(lastPosition).idAccount+1;
            }else{
                this.idAccount = 1;
            }
        }
        return this.idAccount;
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
    public String getWebSiteTitle() {
        return webSiteTitle;
    }
    public void setWebSiteTitle(String webSiteTitle) {
        this.webSiteTitle = webSiteTitle;
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
