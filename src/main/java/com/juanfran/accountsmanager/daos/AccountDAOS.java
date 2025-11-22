package com.juanfran.accountsmanager.daos;

import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IAccountDAOS;
import com.juanfran.accountsmanager.managers.AccountManager;
import com.juanfran.accountsmanager.models.AccountModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        this.accountManager.Accounts.add(account);
    }

    /**
     * Este método se encarga de devolver
     * todos las cuentas del usuario
     * @return List<AccountModel>
     */
    @Override
    public List<AccountModel> getAccounts() {
        return this.accountManager.Accounts;
    }

    /**
     * Este método se encarga de borrar una cuenta
     * personal de la aplicaciópn
     * @param accountModel
     */
    @Override
    public void removeAccount(AccountModel accountModel) {

        this.logger.info("Vamos a borrar la cuenta de " + accountModel.getWebSiteTitle() + " con el nombre " + accountModel.getUserName() + " de la aplicación");

        this.accountManager.deleteAccount("{ Call DeleteAccount (?) }",accountModel.getIdAccount());

        this.accountManager.Accounts.remove(accountModel);

    }

    /**
     * Este método se encarga de buscar
     * una cuenta personal del usuario
     * por su id
     * @param id
     * @return
     */
    @Override
    public AccountModel searchAccountById(Integer id) {
        for(int i = 0; i<this.accountManager.Accounts.size(); i++){
            if(this.accountManager.Accounts.get(i).getIdAccount()==id){
                return this.accountManager.Accounts.get(i);
            }
        }
        return null;
    }

    /**
     * Este método se encarga de actualizar los
     * datos de la cuenta personal del usuario
     * tanto en la aplicación como en la base de datos
     * @param account
     */
    @Override
    public void updateAccount(AccountModel account) {
        this.logger.info("Actualizamos la contraseña");
        for(int i = 0; i<this.accountManager.Accounts.size(); i++){
            if(this.accountManager.Accounts.get(i).getIdAccount()== account.getIdAccount()){
                this.accountManager.updateAccount(" { Call UpdateAccount(?,?,?,?,?) } ",account.getIdAccount(),account.getWebSiteAddress(),account.getUserName(),account.getEmail(),account.getWebSiteTitle());
                this.accountManager.Accounts.set(i,account);
            }
        }
    }

    /**
     * Este método se encarga de actualizar que
     * cuenta personal ha sido removida de la
     * vista Accounts
     * @param isRemoved
     * @param idAccount
     */
    @Override
    public void updateIsRemoved(Integer idAccount,boolean isRemoved) {
        this.logger.info("La cuenta personal " + idAccount + " ha sido removida");
        for(int i = 0; i<this.accountManager.Accounts.size(); i++){
            if(this.accountManager.Accounts.get(i).getIdAccount().equals(idAccount)){
                this.accountManager.updateIsRemoved(" { Call updateIsRemovedFromAccount (?,?) } ",idAccount,isRemoved);
                this.accountManager.Accounts.get(i).setRemoved(isRemoved);
            }
        }
    }

    /**
     * Este método se encarga de devolver
     * una lista de todas las cuentas
     * removidas de la vista Accounts
     * @return
     */
    @Override
    public List<AccountModel> getListAccountsRemoved() {
        List<AccountModel> listAccountsRemoved = new ArrayList<>();
        for(int i = 0; i<this.accountManager.Accounts.size(); i++){
            if(String.valueOf(this.accountManager.Accounts.get(i).isRemoved()).equals(String.valueOf(true))){
                listAccountsRemoved.add(this.accountManager.Accounts.get(i));
            }
        }
        return listAccountsRemoved;
    }
}
