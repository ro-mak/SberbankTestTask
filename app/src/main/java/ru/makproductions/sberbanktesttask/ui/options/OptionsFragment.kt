package ru.makproductions.sberbanktesttask.ui.options

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.makproductions.sberbanktesttask.R
import ru.makproductions.sberbanktesttask.view.options.OptionsView

class OptionsFragment : MvpAppCompatFragment(), OptionsView {
    companion object {
        fun getInstance(): OptionsFragment {
            val fragment = OptionsFragment()
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigation?.menu?.findItem(R.id.options_tab)?.isChecked = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_options, container, false)
        return view
    }
}