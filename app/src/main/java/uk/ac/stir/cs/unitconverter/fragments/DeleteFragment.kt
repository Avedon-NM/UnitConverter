package uk.ac.stir.cs.unitconverter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.convert_fragment.*
import kotlinx.android.synthetic.main.delete_fragment.*
import uk.ac.stir.cs.unitconverter.R
import uk.ac.stir.cs.unitconverter.R.layout
import uk.ac.stir.cs.unitconverter.database.UnitsViewModel


class DeleteFragment : Fragment() {

    // Enables a connection between the "Select Fragment" Fragment with the "Convert Fragment"
    private lateinit var unitsViewModel: UnitsViewModel

    //Variables for storing a list of the required database information
    private var unit1List: List<String> = emptyList()
    private var unit2List: List<String> = emptyList()
    private var customList: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(layout.delete_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        unitsViewModel = ViewModelProvider(this).get(UnitsViewModel::class.java)

        // // Extracting a list of all custom categories in the database to populate the "Conversion Type" spinner
        unitsViewModel.getCustom().observe(viewLifecycleOwner, Observer { units ->
            customList = units
            deleteTypeSpinner.adapter = ArrayAdapter(requireActivity().baseContext,
                        layout.support_simple_spinner_dropdown_item, customList)
        })

        //Updating the "Unit1" and "Unit2" spinners so that their list matches their respective "Conversion Type" category
        deleteTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var currentCategory = deleteTypeSpinner.selectedItem.toString()
                updateUnitSpinners(currentCategory)
                updateUnitSpinners(currentCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        //Triggers the deleteConversionEntry function and also displays a message if the button is pressed whilst the list is empty
        deleteButton.setOnClickListener {

            if(deleteTypeSpinner.selectedItem == null) {
                Toast.makeText(requireContext(), "No custom units left.", Toast.LENGTH_LONG).show()
            } else {
                var unit1 = deleteUnit1.selectedItem.toString()
                var unit2 = deleteUnit2.selectedItem.toString()
                deleteConversionEntry(unit1, unit2)
            }
        }

    }


    //The method called by the "Conversion Type" spinner to update the "Unit1" and "Unit2" spinner
    private fun updateUnitSpinners(category: String) {

        unitsViewModel.getUnit1List(category).observe(viewLifecycleOwner, Observer { units ->
            unit1List = units
            deleteUnit1.adapter = ArrayAdapter(requireActivity().baseContext,
                layout.support_simple_spinner_dropdown_item, unit1List)
        })

        unitsViewModel.getUnit2List(category).observe(viewLifecycleOwner, Observer { units ->
            unit2List = units
            deleteUnit2.adapter = ArrayAdapter(requireActivity().baseContext,
                layout.support_simple_spinner_dropdown_item, unit2List)
        })

        //Current selected items in the "Units" are updated in the Selection Model so that the "Convert Fragment" can access the names and display them
        deleteUnit1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                //Ensures the spinner values are not the same
                while(deleteUnit2.selectedItem.toString() == deleteUnit1.selectedItem.toString()){
                    deleteUnit2.setSelection((0 until 2).random())
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        deleteUnit2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                //Ensures the spinner values are not the same
                while(deleteUnit2.selectedItem.toString() == deleteUnit1.selectedItem.toString()){
                    deleteUnit1.setSelection((0 until 2).random())
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    //Sends the category name to the unit model which is then used to query the database to delete that entry. Displays success message
    private fun deleteConversionEntry(unit1: String, unit2: String) {
        unitsViewModel.deleteConversionEntry(unit1, unit2)
        Toast.makeText(requireContext(), "$unit1 and $unit2 conversion entries successfully deleted", Toast.LENGTH_LONG).show()
    }
}

