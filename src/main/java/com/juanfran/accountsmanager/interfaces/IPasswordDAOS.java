package com.juanfran.accountsmanager.interfaces;

import com.juanfran.accountsmanager.models.PasswordModel;

public interface IPasswordDAOS {
    void updatePassword(Integer idPassword,String password);
    boolean isTheSamePassword(Integer idPassword, String password);
    void registerNewPassword(PasswordModel password);
    PasswordModel getPasswordById(Integer id);
    void updateRecoverPasswordCode(Integer generatedCode, Integer idPassword);
    Integer getRecoverPasswordCode(Integer idPassword);
    void removePassword(Integer idPassword);
}
