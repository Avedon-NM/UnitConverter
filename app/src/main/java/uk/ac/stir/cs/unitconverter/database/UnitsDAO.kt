package uk.ac.stir.cs.unitconverter.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Interface for specifying SQL queries and associate them with method calls.
 */

@Dao
interface UnitsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun populateDatabase(database: ArrayList<ConversionEntry>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun addConversion(conversionEntry: ConversionEntry)

    @Query("SELECT DISTINCT unit1 FROM conversion_table WHERE category LIKE :category")
    fun getUnit1List(category: String): LiveData<List<String>>

    @Query("SELECT DISTINCT unit2 FROM conversion_table WHERE category LIKE :category")
    fun getUnit2List(category: String): LiveData<List<String>>

    @Query("SELECT DISTINCT category FROM conversion_table")
    fun getCategoryList(): LiveData<List<String>>

    @Query("SELECT DISTINCT category FROM conversion_table WHERE custom LIKE 'True'")
    fun getCustom(): LiveData<List<String>>

    @Query("SELECT formula FROM conversion_table WHERE unit1 LIKE :unit1 AND unit2 LIKE :unit2")
    fun getFormula(unit1: String, unit2: String): LiveData<String>

    @Query("DELETE FROM conversion_table WHERE unit1 LIKE :unit1 AND unit2 LIKE :unit2")
    fun deleteConversionEntry(unit1: String, unit2: String)

}