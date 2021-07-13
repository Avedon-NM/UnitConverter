package uk.ac.stir.cs.unitconverter


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import uk.ac.stir.cs.unitconverter.database.ConversionEntry
import uk.ac.stir.cs.unitconverter.database.UnitsViewModel

class MainActivity : AppCompatActivity() {

    // Used to connect app to the database, in this activity it will be used to populate the database
    private lateinit var unitsModelView: UnitsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.select_unit))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.convert_unit))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.create_unit))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.delete_unit))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val adapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

        unitsModelView = ViewModelProvider(this).get(UnitsViewModel::class.java)
        populateDatabase()
    }

    // Generates an array list of ConversionEntries and sends it to the UnitsViewModel which will pass it to the database
    private fun populateDatabase(){

        val conversionData = arrayListOf<ConversionEntry>()
        val GRAM_TO_KILOGRAM = ConversionEntry("Gram", "Kilogram", "0.001", "Weight", "False")
        val GRAM_TO_POUND = ConversionEntry("Gram", "Pound", "0.00220462", "Weight", "False")
        val GRAM_TO_STONE = ConversionEntry("Gram", "Stone", "0.000157473", "Weight", "False")

        val KILOGRAM_TO_GRAM = ConversionEntry("Kilogram", "Gram", "1000", "Weight", "False")
        val KILOGRAM_TO_POUND = ConversionEntry("Kilogram", "Pound", "2.20462", "Weight", "False")
        val KILOGRAM_TO_STONE = ConversionEntry("Kilogram", "Stone", "0.157473", "Weight", "False")

        val POUND_TO_GRAM = ConversionEntry("Pound", "Gram", "453.592", "Weight", "False")
        val POUND_TO_KILOGRAM = ConversionEntry("Pound", "Kilogram", "0.453592", "Weight", "False")
        val POUND_TO_STONE = ConversionEntry("Pound", "Stone", "0.0714286", "Weight", "False")

        val STONE_TO_GRAM = ConversionEntry("Stone", "Gram", "6350.293", "Weight", "False")
        val STONE_TO_KILOGRAM = ConversionEntry("Stone", "Kilogram", "6.35029", "Weight", "False")
        val STONE_TO_POUND = ConversionEntry("Stone", "Pound", "14", "Weight", "False")


        val METRE_TO_KILOMETRE = ConversionEntry("Metre", "Kilometre", "0.001", "Length", "False")
        val METRE_TO_FOOT = ConversionEntry("Metre", "Foot", "3.28084", "Length", "False")
        val METRE_TO_MILE = ConversionEntry("Metre", "Mile", "0.000621371", "Length", "False")

        val KILOMETRE_TO_METRE = ConversionEntry("Kilometre", "Metre", "1000", "Length", "False")
        val KILOMETRE_TO_FOOT = ConversionEntry("Kilometre", "Foot", "3280.84", "Length", "False")
        val KILOMETRE_TO_MILE = ConversionEntry("Kilometre", "Mile", "0.621371", "Length", "False")

        val FOOT_TO_METRE = ConversionEntry("Foot", "Metre", "0.3048", "Length", "False")
        val FOOT_TO_KILOMETRE = ConversionEntry("Foot", "Kilometre", "0.0003048", "Length", "False")
        val FOOT_TO_MILE = ConversionEntry("Foot", "Mile", "0.000189394", "Length", "False")

        val MILE_TO_METRE = ConversionEntry("Mile", "Metre", "1609.34", "Length", "False")
        val MILE_TO_KILOMETRE = ConversionEntry("Mile", "Kilometre", "1.60934", "Length", "False")
        val MILE_TO_FOOT = ConversionEntry("Mile", "Foot", "5280", "Length", "False")


        val MILLILITRE_TO_LITRE = ConversionEntry("Millilitre", "Litre", "0.001", "Volume", "False")
        val MILLILITRE_TO_CUP = ConversionEntry("Millilitre", "Cup", "0.00351951", "Volume", "False")
        val MILLILITRE_TO_PINT = ConversionEntry("Millilitre", "Pint", "0.00175975", "Volume", "False")

        val LITRE_TO_MILLILITRE = ConversionEntry("Litre", "Millilitre", "1000", "Volume", "False")
        val LITRE_TO_CUP = ConversionEntry("Litre", "Cup", "3.51951", "Volume", "False")
        val LITRE_TO_PINT = ConversionEntry("Litre", "Pint", "1.75975", "Volume", "False")

        val CUP_TO_MILLILITRE = ConversionEntry("Cup", "Millilitre", "284.131", "Volume", "False")
        val CUP_TO_LITRE = ConversionEntry("Cup", "Litre", "0.284131", "Volume", "False")
        val CUP_TO_PINT = ConversionEntry("Cup", "Pint", "0.5", "Volume", "False")

        val PINT_TO_MILLILITRE = ConversionEntry("Pint", "Millilitre", "568.261", "Volume", "False")
        val PINT_TO_LITRE = ConversionEntry("Pint", "Litre", "0.568261", "Volume", "False")
        val PINT_TO_CUP = ConversionEntry("Pint", "Cup", "2", "Volume", "False")


        val SECOND_TO_MINUTE = ConversionEntry("Second", "Minute", "0.0166667", "Time", "False")
        val SECOND_TO_HOUR = ConversionEntry("Second", "Hour", "0.000277778", "Time", "False")
        val SECOND_TO_DAY = ConversionEntry("Second", "Day", "0.000011574", "Time", "False")

        val MINUTE_TO_SECOND = ConversionEntry("Minute", "Second", "60", "Time", "False")
        val MINUTE_TO_HOUR = ConversionEntry("Minute", "Hour", "0.0166667", "Time", "False")
        val MINUTE_TO_DAY = ConversionEntry("Minute", "Day", "0.000694444", "Time", "False")

        val HOUR_TO_SECOND = ConversionEntry("Hour", "Second", "3600", "Time", "False")
        val HOUR_TO_MINUTE = ConversionEntry("Hour", "Minute", "60", "Time", "False")
        val HOUR_TO_DAY = ConversionEntry("Hour", "Day", "0.0416667", "Time", "False")

        val DAY_TO_SECOND = ConversionEntry("Day", "Second", "86400", "Time", "False")
        val DAY_TO_MINUTE = ConversionEntry("Day", "Minute", "1440", "Time", "False")
        val DAY_TO_HOUR = ConversionEntry("Day", "Hour", "24", "Time", "False")

        conversionData.add(GRAM_TO_KILOGRAM)
        conversionData.add(GRAM_TO_POUND)
        conversionData.add(GRAM_TO_STONE)

        conversionData.add(KILOGRAM_TO_GRAM)
        conversionData.add(KILOGRAM_TO_POUND)
        conversionData.add(KILOGRAM_TO_STONE)

        conversionData.add(POUND_TO_GRAM)
        conversionData.add(POUND_TO_KILOGRAM)
        conversionData.add(POUND_TO_STONE)

        conversionData.add(STONE_TO_GRAM)
        conversionData.add(STONE_TO_KILOGRAM)
        conversionData.add(STONE_TO_POUND)

        conversionData.add(METRE_TO_KILOMETRE)
        conversionData.add(METRE_TO_FOOT)
        conversionData.add(METRE_TO_MILE)

        conversionData.add(KILOMETRE_TO_METRE)
        conversionData.add(KILOMETRE_TO_FOOT)
        conversionData.add(KILOMETRE_TO_MILE)

        conversionData.add(FOOT_TO_METRE)
        conversionData.add(FOOT_TO_KILOMETRE)
        conversionData.add(FOOT_TO_MILE)

        conversionData.add(MILE_TO_METRE)
        conversionData.add(MILE_TO_KILOMETRE)
        conversionData.add(MILE_TO_FOOT)

        conversionData.add(MILLILITRE_TO_LITRE)
        conversionData.add(MILLILITRE_TO_CUP)
        conversionData.add(MILLILITRE_TO_PINT)

        conversionData.add(LITRE_TO_MILLILITRE)
        conversionData.add(LITRE_TO_CUP)
        conversionData.add(LITRE_TO_PINT)

        conversionData.add(CUP_TO_MILLILITRE)
        conversionData.add(CUP_TO_LITRE)
        conversionData.add(CUP_TO_PINT)

        conversionData.add(PINT_TO_MILLILITRE)
        conversionData.add(PINT_TO_LITRE)
        conversionData.add(PINT_TO_CUP)

        conversionData.add(SECOND_TO_MINUTE)
        conversionData.add(SECOND_TO_HOUR)
        conversionData.add(SECOND_TO_DAY)

        conversionData.add(MINUTE_TO_SECOND)
        conversionData.add(MINUTE_TO_HOUR)
        conversionData.add(MINUTE_TO_DAY)

        conversionData.add(HOUR_TO_SECOND)
        conversionData.add(HOUR_TO_MINUTE)
        conversionData.add(HOUR_TO_DAY)

        conversionData.add(DAY_TO_SECOND)
        conversionData.add(DAY_TO_MINUTE)
        conversionData.add(DAY_TO_HOUR)


        unitsModelView.populateDatabase(conversionData)

    }

}


