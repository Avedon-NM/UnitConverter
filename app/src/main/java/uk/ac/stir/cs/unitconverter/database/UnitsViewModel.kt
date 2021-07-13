package uk.ac.stir.cs.unitconverter.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Connects to the app to the repository and also enables fragments to communicate with each other.
 */
class UnitsViewModel(application: Application): AndroidViewModel(application){

    //Variables which the app needs to monitor changes to
    private val categoryList: LiveData<List<String>>
    private val customList: LiveData<List<String>>
    private lateinit var unit1List: LiveData<List<String>>
    private lateinit var unit2List: LiveData<List<String>>
    private val repository: UnitsRepository

    init {
        val unitsDao = ConversionDatabase.getDatabase(application).unitsDAO()
        repository = UnitsRepository(unitsDao)
        categoryList = repository.getCategoryList()
        customList = repository.getCustom()
    }

    fun addConversion(conversionEntry: ConversionEntry){
        viewModelScope.launch(Dispatchers.IO){
            repository.addConversion(conversionEntry)
        }
    }

    fun populateDatabase(data: ArrayList<ConversionEntry>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.populateDatabase(data)
        }
    }

    fun getUnit1List(category: String): LiveData<List<String>> {
        unit1List = repository.getUnit1List(category)
        return unit1List
    }

    fun getUnit2List(category: String): LiveData<List<String>> {
        unit2List = repository.getUnit2List(category)
        return  unit2List
    }

    fun getCategoryList(): LiveData<List<String>>{
        return categoryList
    }

    fun getFormula(unit1: String, unit2: String): LiveData<String> {
        return repository.getFormula(unit1, unit2)
    }

    fun getCustom(): LiveData<List<String>> {
        return customList
    }

    fun deleteConversionEntry(unit1: String, unit2: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteConversionEntry(unit1, unit2)
        }
    }
}