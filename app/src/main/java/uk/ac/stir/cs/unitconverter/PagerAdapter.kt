package uk.ac.stir.cs.unitconverter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uk.ac.stir.cs.unitconverter.fragments.ConvertFragment
import uk.ac.stir.cs.unitconverter.fragments.CreateFragment
import uk.ac.stir.cs.unitconverter.fragments.DeleteFragment
import uk.ac.stir.cs.unitconverter.fragments.SelectFragment

internal class PagerAdapter(fragmentManager: FragmentManager?, private val mNumOfTabs: Int)
    : FragmentStatePagerAdapter(fragmentManager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SelectFragment()
            1 -> ConvertFragment()
            2 -> CreateFragment()
            3 -> DeleteFragment()
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}