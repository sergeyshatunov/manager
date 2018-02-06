package by.shatunov.manager.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import by.shatunov.manager.accounts.Account
import by.shatunov.manager.accounts.AccountRepository
import by.shatunov.manager.database.ManagerDatabase
import by.shatunov.manager.transactions.Transaction
import by.shatunov.manager.transactions.TransactionRepository

class ManagerViewModel(application: Application) : AndroidViewModel(application) {

    private val db = ManagerDatabase.create(getApplication())
    private val accountRepository = AccountRepository(db.managerDao)
    private val transactionRepository = TransactionRepository(db.managerDao)


    fun getAllAccounts(): LiveData<List<Account>> {
        return accountRepository.getAllAccounts()
    }

    fun getAccountById(id: Int): LiveData<Account>? {
        return accountRepository.getAccountById(id)
    }

    fun createAccount(account: Account) {
        CreateAccountTask(accountRepository).execute(account)
    }

    private class CreateAccountTask(private val repository: AccountRepository) : AsyncTask<Account, Void, Void>() {
        override fun doInBackground(vararg params: Account): Void? {
            repository.createAccount(params[0])
            return null
        }
    }

    fun updateAccount(account: Account) {
        UpdateAccountTask(accountRepository).execute(account)
    }

    class UpdateAccountTask(private val repository: AccountRepository) : AsyncTask<Account, Void, Void>() {
        override fun doInBackground(vararg params: Account): Void? {
            repository.updateAccount(params[0])
            return null
        }
    }

    fun deleteAccount(account: Account) {
        DeleteAccountTask(accountRepository).execute(account)
    }

    class DeleteAccountTask(private val repository: AccountRepository) : AsyncTask<Account, Void, Void>() {
        override fun doInBackground(vararg params: Account): Void? {
            repository.deleteAccount(params[0])
            return null
        }
    }


    fun getAllTransactions(): LiveData<List<Transaction>> {
        return transactionRepository.getAllTransactions()
    }

    fun createTransaction(transaction: Transaction, account: Account) {
        CreateTransactionTask(transactionRepository, account).execute(transaction)
    }

    class CreateTransactionTask(private val transactionRepository: TransactionRepository, private val account: Account) :
            AsyncTask<Transaction, Void, Void>() {
        override fun doInBackground(vararg params: Transaction): Void? {
            transactionRepository.createTransaction(params[0], account)
            return null
        }

    }

    override fun onCleared() {
        super.onCleared()
    }
}
