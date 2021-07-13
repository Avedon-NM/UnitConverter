package uk.ac.stir.cs.unitconverter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.select_fragment.*
import uk.ac.stir.cs.unitconverter.R.layout
import uk.ac.stir.cs.unitconverter.UnitSelectionModel
import uk.ac.stir.cs.unitconverter.database.UnitsViewModel


class SelectFragment : Fragment() {

    private lateinit var unitsViewModel: UnitsViewModel  // Connects the app to the Room database

    private val unitSelectionModel: UnitSelectionModel by activityViewModels() // Enables a connection between the "Select Fragment" Fragment with the "Convert Fragment"

    //Variables for storing a list of the required database information
    var unit1List: List<String> = emptyList()
    var unit2List: List<String> = emptyList()
    var categoryList: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(layout.select_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        unitsViewModel = ViewModelProvider(this).get(UnitsViewModel::class.java)


        // Extracting a list of all categories list in the database to populate the "Conversion Type" spinner
        unitsViewModel = ViewModelProvider(this).get(UnitsViewModel::class.java)

        // // Extracting a list of all custom categories in the database to populate the "Conversion Type" spinner
        unitsViewModel.getCategoryList().observe(viewLifecycleOwner, Observer { unitsList ->
            categoryList = unitsList
            unitTypeSpinner.adapter = ArrayAdapter(requireActivity().baseContext,
                layout.support_simple_spinner_dropdown_item, categoryList)
        })

        //Updating the "Units" spinners so that their list matches their respective "Conversion Type" category
        unitTypeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long){
                updateUnitSpinners(unitTypeSpinner.selectedItem.toString())
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

    }

    // The method called by the "Conversion Type" spinner to update the "Convert From" and "Convert To" spinner
    private fun updateUnitSpinners(category: String) {

        //Extracting a list of all custom categories in the database to populate the "Conversion Type" spinner
        unitsViewModel.getUnit1List(category).observe(viewLifecycleOwner, Observer { units ->
            unit1List = units
            convertFromSpinner.adapter = ArrayAdapter(requireActivity().baseContext,
                layout.support_simple_spinner_dropdown_item, unit1List)
        })

        unitsViewModel.getUnit2List(category).observe(viewLifecycleOwner, Observer { units ->
            unit2List = units
            convertToSpinner.adapter = ArrayAdapter(requireActivity().baseContext,
                layout.support_simple_spinner_dropdown_item, unit2List)
        })

            //Current selected items in the "Units" are updated in the Selection Model so that the "Convert Fragment" can access the names and display them
            convertFromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    //Ensures the ConvertToSpinner and the ConvertFromSpinner are not pointing at the same values
                    while(convertToSpinner.selectedItem.toString() == convertFromSpinner.selectedItem.toString()){
                        convertToSpinner.setSelection((0 until 2).random())
                    }
                    onUnitOneSelection(convertFromSpinner.selectedItem.toString())
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        convertToSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //Ensures the ConvertToSpinner and the ConvertFromSpinner values are not the same
                while(convertToSpinner.selectedItem.toString() == convertFromSpinner.selectedItem.toString()){
                    convertFromSpinner.setSelection((0 until 2).random())
                }
                onUnitTwoSelection(convertToSpinner.selectedItem.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        }

    //Methods for updating the variables in the Selection Model
    fun onUnitOneSelection(unit1:String) = unitSelectionModel.setUnit1(unit1)
    fun onUnitTwoSelection(unit2:String) = unitSelectionModel.setUnit2(unit2)
}