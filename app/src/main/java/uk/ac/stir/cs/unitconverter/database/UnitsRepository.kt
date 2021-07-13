package uk.ac.stir.cs.unitconverter.database

import androidx.lifecycle.LiveData

/**
 * Handles data operations and provides a tidy API to the app.
 */
class UnitsRepository(private val unitsDao: UnitsDAO) {

    suspend fun addConversion(conversionEntry: ConversionEntry){
        unitsDao.addConversion(conversionEntry)
    }

    suspend fun populateDatabase(data: ArrayList<ConversionEntry>){
        unitsDao.populateDatabase(data)
    }

    fun getUnit1List(category: String): LiveData<List<String>>{
        return unitsDao.getUnit1List(category)
    }

    fun getUnit2List(category: String): LiveData<List<String>>{
        return unitsDao.getUnit2List(category)
    }

    fun getCategoryList(): LiveData<List<String>>{
        return unitsDao.getCategoryList()
    }

    fun getFormula(unit1: String, unit2: String): LiveData<String>{
        return unitsDao.getFormula(unit1, unit2)
    }

    fun getCustom(): LiveData<List<String>>{
        return unitsDao.getCustom()
    }

    fun deleteConversionEntry(unit1: String, unit2: String){
        unitsDao.deleteConversionEntry(unit1, unit2)
    }
}