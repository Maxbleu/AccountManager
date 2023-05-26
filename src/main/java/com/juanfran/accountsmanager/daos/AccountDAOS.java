package com.juanfran.accountsmanager.daos;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IAccountDAOS;
import com.juanfran.accountsmanager.managers.AccountManager;
import com.juanfran.accountsmanager.models.AccountModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDAOS implements IAccountDAOS {
    private final AccountManager accountManager;
    private final Logger logger;
    @Autowired
    public AccountDAOS(AccountManager accountManager){
        this.accountManager = accountManager;
        this.logger = OrchestratorProyectDependences.getLogger();
    }

    /**
     * Este método se utiliza para insertar una
     * nueva cuenta de un usuario en la aplicación
     * @param account
     */
    @Override
    public void registerNewAccount(AccountModel account) {

        this.logger.info("Insertamos la cuenta ".concat(account.getUserName()) + " en la base de datos");

        //  Insertamos la cuenta en la base de datos
        this.accountManager.insertAccount(" { Call insertAccount (?,?,?,?,?,?,?) } ",account);

        //  Insertamos la cuenta en la lista
        AccountManager.Accounts.add(account);
    }

    /**
     * Este método se encarga de devolver
     * todos las cuentas del usuario
     * @return List<AccountModel>
     */
    @Override
    public List<AccountModel> getAccounts() {
        return AccountManager.Accounts;
    }

    /**
     * Este método se encarga de borrar una cuenta
     * personal de la aplicaciópn
     * @param accountModel
     */
    @Override
    public void removeAccount(AccountModel accountModel) {

        this.logger.info("Vamos a borrar la cuenta de " + accountModel.getWebSiteTitle() + " con el nombre " + accountModel.getUserName() + " de la aplicación");

        this.accountManager.deleteAccount("{ Call DeleteAccount (?) }",accountModel);

        AccountManager.Accounts.remove(accountModel);

    }
}
