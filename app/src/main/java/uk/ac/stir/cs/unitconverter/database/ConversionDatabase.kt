package uk.ac.stir.cs.unitconverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ConversionEntry::class], version = 1, exportSchema = false)
abstract class ConversionDatabase : RoomDatabase() {

/**
 * This class instantiates the database
 */

    abstract fun unitsDAO(): UnitsDAO

    companion object {

        @Volatile
        private var INSTANCE: ConversionDatabase? = null

        fun getDatabase(context: Context): ConversionDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ConversionDatabase::class.java,
                    "conversion_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }

}