package uk.ac.stir.cs.unitconverter.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.create_fragment.*
import uk.ac.stir.cs.unitconverter.R.layout
import uk.ac.stir.cs.unitconverter.database.ConversionEntry
import uk.ac.stir.cs.unitconverter.database.UnitsViewModel

class CreateFragment : Fragment() {

    private lateinit var unitsViewModel: UnitsViewModel // Enables a connection between the "Select Fragment" Fragment with the "Convert Fragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(layout.create_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        unitsViewModel = ViewModelProvider(this).get(UnitsViewModel::class.java)

        addConversionButton.setOnClickListener{
            addConversionToDatabase()
        }

        clearButton.setOnClickListener{
            clear()
        }

    }

    //Extracts the data entered and passes it to the database
    private fun addConversionToDatabase() {

        //If the the values are the same and that value isnt an empty string
        if(addUnit1.text.toString() == addUnit2.text.toString() && addUnit1.text.toString() != ""){
            Toast.makeText(requireContext(), "Unit 1 cannot be the same name as Unit 2.", Toast.LENGTH_LONG).show()
        } else {

            val conversionType = addConversionType.text.toString()
            val unit1 = addUnit1.text.toString()
            val unit2 = addUnit2.text.toString()
            val formula = addFormula.text.toString()
            val custom = "True"

            // Notify user when data is added.
            if(inputCheck(conversionType, unit1, unit2, formula)){
                var entry = ConversionEntry(unit1, unit2, formula, conversionType, custom)
                unitsViewModel.addConversion(entry)
                var reverseUnit1 = unit2
                var reverseUnit2 = unit1
                entry = ConversionEntry(reverseUnit1, reverseUnit2, formula, conversionType, custom)
                unitsViewModel.addConversion(entry)
                Toast.makeText(requireContext(), "New Conversion Added!", Toast.LENGTH_LONG).show()
                clear()
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
            }
        }


    }

    //Ensures that all fields are entered before being passed to the database.
    private fun inputCheck(conversionType: String, unit1: String, unit2: String, formula: String): Boolean{
        return !(unit1.isEmpty() || unit2.isEmpty() || conversionType.isEmpty() || formula.isEmpty())
    }

    //Clears all the fields
    private fun clear(){
        addConversionType.text.clear()
        addUnit1.text.clear()
        addUnit2.text.clear()
        addFormula.text.clear()
    }

}