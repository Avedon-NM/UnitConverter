package uk.ac.stir.cs.unitconverter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Variables are set in SelectFragment and observed in ConvertFragment
 */

class UnitSelectionModel : ViewModel() {
    private val unit1 = MutableLiveData<String>()
    private val unit2 = MutableLiveData<String>()

    fun setUnit1(selection1: String) {
        unit1.value = selection1
    }

    fun setUnit2(selection2: String) {
        unit2.value = selection2
    }

    fun getUnit1(): MutableLiveData<String>{
        return unit1
    }

    fun getUnit2(): MutableLiveData<String>{
        return unit2
    }


}
