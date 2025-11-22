package com.juanfran.accountsmanager.interfaces;

import com.juanfran.accountsmanager.models.AccountModel;

import java.util.List;

public interface IAccountDAOS {
    void registerNewAccount(AccountModel account);
    List<AccountModel> getAccounts();
    void removeAccount(AccountModel accountModel);
    AccountModel searchAccountById(Integer id);
    void updateAccount(AccountModel account);
    void updateIsRemoved(Integer idAccount ,boolean isRemoved);
    List<AccountModel> getListAccountsRemoved();
}
