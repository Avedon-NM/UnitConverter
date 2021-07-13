package uk.ac.stir.cs.unitconverter.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.convert_fragment.*
import uk.ac.stir.cs.unitconverter.R.layout
import uk.ac.stir.cs.unitconverter.UnitSelectionModel
import uk.ac.stir.cs.unitconverter.database.UnitsViewModel


class ConvertFragment : Fragment() {

    private lateinit var unitsViewModel: UnitsViewModel // Connects application to the database
    private val unitSelectionModel: UnitSelectionModel by activityViewModels() // Enables a connection between the "Select Fragment" Fragment with the "Convert Fragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(layout.convert_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        unitsViewModel = ViewModelProvider(this).get(UnitsViewModel::class.java)

        //Observes the values in the Selection Model and displays any changes to the text view
        unitSelectionModel.getUnit1().observe(viewLifecycleOwner, Observer { unit1 ->
            convertedFromLabel.text = unit1
        })

        unitSelectionModel.getUnit2().observe(viewLifecycleOwner, Observer { unit2 ->
            convertedToLabel.text = unit2
        })

        //Extracts the text in the Unit names as well as the input value and passes this information in the "getFormula" function
        convertButton.setOnClickListener {
            var unit1 = convertedFromLabel.text.toString()
            var unit2 = convertedToLabel.text.toString()

            //Making sure the field is not empty
            if(convertedFromValue.text.toString() == ""){
                Toast.makeText(requireContext(), "Please enter the number of units", Toast.LENGTH_LONG).show()
            } else{
                val input = convertedFromValue.text.toString()
                getFormula(unit1, unit2, input)
            }

        }

        // Clears the fields on the interface
        clearButton.setOnClickListener {
            convertedFromValue.text = null
            convertedToValue.text = ""
        }

    }

    /**
     * Function uses the unit1 and unit2 data from the TextView to query the database and extract a corresponding formula value.
     * The input number and the formula value are converted from String data type to a corresponding number data type to enable calculation.
     * The result is converted back to a String so it can be displayed on the TextView
     */
    private fun getFormula(unit1: String, unit2: String, input: String) {

        unitsViewModel.getFormula(unit1, unit2)
            .observe(viewLifecycleOwner) { formula ->
                    if (formula.contains(".") && input.contains(".")) {
                        convertedToValue.text = (formula.toDouble() * input.toDouble()).toString()
                    } else if (!formula.contains(".") && !input.contains(".")) {
                        convertedToValue.text = (formula.toInt() * input.toInt()).toString()
                    } else if (formula.contains(".") && !input.contains(".")) {
                        convertedToValue.text = (formula.toDouble() * input.toInt()).toString()
                    } else {
                        convertedToValue.text = (formula.toInt() * input.toDouble()).toString()
                    }
                }

            }
    }

