package by.shatunov.manager.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import by.shatunov.manager.accounts.Account
import by.shatunov.manager.transactions.Transaction

@Dao
abstract class ManagerDao {

    @Query("select * from accounts")
    abstract fun getAllAccounts(): LiveData<List<Account>>

    @Query("select * from accounts where id = :id")
    abstract fun getAccountById(id: Int): LiveData<Account>

    @Insert
    abstract fun insertAccount(account: Account)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateAccount(account: Account)

    @Delete
    abstract fun deleteAccount(account: Account)


    @Query("select * from transactions")
    abstract fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("select * from transactions where id = :id")
    abstract fun getTransactionById(id: Int): LiveData<Transaction>

    @Insert
    abstract fun insertTransaction(transaction: Transaction)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateTransaction(transaction: Transaction)

    @Delete
    abstract fun deleteTransaction(transaction: Transaction)


    @android.arch.persistence.room.Transaction
    open fun insert(transaction: Transaction, account: Account) {
        insertTransaction(transaction)
        updateAccount(account)
    }
}
