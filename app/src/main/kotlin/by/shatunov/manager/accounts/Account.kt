package by.shatunov.manager.accounts

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "accounts")
class Account(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var accountId: Int,
              @ColumnInfo(name = "current_amount") var accountCurrentAmount: Int,
              @ColumnInfo(name = "init_date") var accountInitDate: Date,
              @ColumnInfo(name = "title") var accountTitle: String)
