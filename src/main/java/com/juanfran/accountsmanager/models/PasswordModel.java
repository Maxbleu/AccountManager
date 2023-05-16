package com.juanfran.accountsmanager.models;

import com.juanfran.accountsmanager.managers.PasswordManager;
import com.juanfran.accountsmanager.models.enums.SecurityPassword;

public class PasswordModel {
    private Integer idPassword;
    private String password;
    private Integer recuperationCode;
    private SecurityPassword securityPassword;

    public PasswordModel(Integer idPassword, String password) {
        this.idPassword = idPassword;
        this.password = password;
    }

    public PasswordModel(String password){
        this.password = password;
    }

    public PasswordModel(){}

    public Integer getIdPassword() {
        if(this.idPassword==null){
            if(PasswordManager.Passwords.isEmpty()){
                this.idPassword = PasswordManager.Passwords.get(PasswordManager.Passwords.size()-1).getIdPassword()+1;
            }else{
                this.idPassword = 1;
            }
        }
        return idPassword;
    }

    public void setIdPassword(Integer idPassword) {
        this.idPassword = idPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRecuperationCode() {
        return recuperationCode;
    }

    public void setRecuperationCode(Integer recuperationCode) {
        this.recuperationCode = recuperationCode;
    }

    public void calculateSecurityPassword() {
    }
}
