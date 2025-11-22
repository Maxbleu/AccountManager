package com.juanfran.accountsmanager.models;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
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
            PasswordManager passwordManager = (PasswordManager) OrchestratorProyectDependences.getService(PasswordManager.class);
            if(passwordManager.Passwords.size() > 0){
                Integer lastPosition = passwordManager.Passwords.size()-1;
                this.idPassword = passwordManager.Passwords.get(lastPosition).idPassword+1;
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
