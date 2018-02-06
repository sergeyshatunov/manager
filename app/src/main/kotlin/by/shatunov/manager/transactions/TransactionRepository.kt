package by.shatunov.manager.transactions

import android.arch.lifecycle.LiveData
import by.shatunov.manager.dao.ManagerDao
import by.shatunov.manager.accounts.Account

class TransactionRepository(private val dao: ManagerDao) {

    fun getAllTransactions(): LiveData<List<Transaction>> {
        return dao.getAllTransactions()
    }

    fun getTransactionById(id: Int): LiveData<Transaction> {
        return dao.getTransactionById(id)
    }

    fun createTransaction(transaction: Transaction, account: Account) {
        return dao.insert(transaction, account)
    }

    fun updateTransaction(transaction: Transaction) {
        return dao.updateTransaction(transaction)
    }

    fun deleteTransaction(transaction: Transaction) {
        return dao.deleteTransaction(transaction)
    }
}
