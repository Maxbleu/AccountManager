package com.juanfran.accountsmanager.models;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.managers.AccountManager;

public class AccountModel {
    private Integer idAccount;
    private String webSiteTitle;
    private String webSiteAddress;
    private String userName;
    private String email;
    private Integer idPassword;
    private Integer idUser;
    private boolean isRemoved;

    public AccountModel(Integer idAccount, String webSiteAddress, String webSiteTitle, String userName, String email, Integer password, Integer idUser) {
        this.idAccount = idAccount;
        this.webSiteAddress = webSiteAddress;
        this.webSiteTitle = webSiteTitle;
        this.email = email;
        this.userName = userName;
        this.idPassword = password;
        this.idUser = idUser;
    }

    public AccountModel(String webSiteAddress, String webSiteTitle, String userName, String email, Integer idPassword) {
        this.webSiteAddress = webSiteAddress;
        this.webSiteTitle = webSiteTitle;
        this.email = email;
        this.userName = userName;
        this.idPassword = idPassword;
    }

    public AccountModel() {}

    public Integer getIdAccount() {
        if(this.idAccount == null){
            AccountManager accountManager = (AccountManager) OrchestratorProyectDependences.getService(AccountManager.class);
            if(accountManager.Accounts.size() > 0){
                Integer lastPosition = accountManager.Accounts.size()-1;
                this.idAccount = accountManager.Accounts.get(lastPosition).idAccount+1;
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

    public Integer getIdPassword() {
        return idPassword;
    }

    public void setIdPassword(Integer password) {
        this.idPassword = password;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
    public boolean isRemoved() {
        return isRemoved;
    }
    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }
}
