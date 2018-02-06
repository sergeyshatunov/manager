package by.shatunov.manager.accounts

import android.arch.lifecycle.LiveData
import by.shatunov.manager.dao.ManagerDao

class AccountRepository(private val dao: ManagerDao) {

    fun getAllAccounts(): LiveData<List<Account>> {
        return dao.getAllAccounts()
    }

    fun getAccountById(id: Int): LiveData<Account> {
        return dao.getAccountById(id)
    }

    fun createAccount(account: Account) {
        return dao.insertAccount(account)
    }

    fun updateAccount(account: Account) {
        return dao.updateAccount(account)
    }

    fun deleteAccount(account: Account) {
        return dao.deleteAccount(account)
    }
}
