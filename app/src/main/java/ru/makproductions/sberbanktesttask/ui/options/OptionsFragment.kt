package ru.makproductions.sberbanktesttask.ui.options

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.makproductions.sberbanktesttask.R
import ru.makproductions.sberbanktesttask.common.Prefs
import ru.makproductions.sberbanktesttask.ui.main.MainActivity
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
        initButtons(view)
        return view
    }

    private fun initButtons(view: View) {
        val radioGroup = view.findViewById<RadioGroup>(R.id.theme_radio_group)
        val themeButton: Int
        when (Prefs.theme) {
            R.style.OrangeTheme -> themeButton = R.id.orange_theme_radio_button
            R.style.GreenTheme -> themeButton = R.id.green_theme_radio_button
            R.style.BlueTheme -> themeButton = R.id.blue_theme_radio_button
            else -> themeButton = R.id.green_theme_radio_button
        }
        radioGroup.check(themeButton)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.green_theme_radio_button -> {
                    Prefs.setThemeId(R.style.GreenTheme)
                    startMainActivity()
                }
                R.id.blue_theme_radio_button -> {
                    Prefs.setThemeId(R.style.BlueTheme)
                    startMainActivity()
                }
                R.id.orange_theme_radio_button -> {
                    Prefs.setThemeId(R.style.OrangeTheme)
                    startMainActivity()
                }
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = intent.flags or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(intent)
    }

}