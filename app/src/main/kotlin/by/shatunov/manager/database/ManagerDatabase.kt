package by.shatunov.manager.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import by.shatunov.manager.accounts.Account
import by.shatunov.manager.converter.DateConverter
import by.shatunov.manager.dao.ManagerDao
import by.shatunov.manager.transactions.Transaction

@Database(entities = [(Account::class), (Transaction::class)], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ManagerDatabase : RoomDatabase() {

    abstract val managerDao: ManagerDao

    companion object {
        private const val dbName = "manager.db"
        fun create(context: Context, memoryOnly: Boolean = false) = when {
            memoryOnly -> Room.inMemoryDatabaseBuilder(context.applicationContext, ManagerDatabase::class.java).build()
            else -> Room.databaseBuilder(context.applicationContext, ManagerDatabase::class.java, dbName).build()
        }
    }
}
