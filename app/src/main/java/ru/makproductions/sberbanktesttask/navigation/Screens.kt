package ru.makproductions.sberbanktesttask.navigation

import android.support.v4.app.Fragment
import ru.makproductions.sberbanktesttask.ui.history.HistoryFragment
import ru.makproductions.sberbanktesttask.ui.options.OptionsFragment
import ru.makproductions.sberbanktesttask.ui.translate.TranslationFragment
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens : Screen() {

    companion object {
        class TranslationScreen : SupportAppScreen() {
            override fun getFragment(): Fragment {
                return TranslationFragment.getInstance()
            }
        }

        class HistoryScreen : SupportAppScreen() {
            override fun getFragment(): Fragment {
                return HistoryFragment.getInstance()
            }
        }

        class OptionsScreen : SupportAppScreen() {
            override fun getFragment(): Fragment {
                return OptionsFragment.getInstance()
            }
        }
    }

}