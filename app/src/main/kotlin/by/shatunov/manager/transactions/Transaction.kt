package by.shatunov.manager.transactions

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "transactions")
class Transaction(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var transactionId: Int,
                  @ColumnInfo(name = "title") var transactionTitle: String,
                  @ColumnInfo(name = "account_id") var transactionAccountId: Int,
                  @ColumnInfo(name = "amount") var transactionAmount: Int,
                  @ColumnInfo(name = "transaction_date") var transactionDate: Date)
