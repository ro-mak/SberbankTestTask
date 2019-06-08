package ru.makproductions.sberbanktesttask.presenter.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.makproductions.sberbanktesttask.navigation.Screens
import ru.makproductions.sberbanktesttask.view.main.MainView
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class MainPresenter : MvpPresenter<MainView>(), KoinComponent {
    val router: Router by inject()
    fun onCreate() {
        Timber.e("onCreate")
        router.navigateTo(Screens.Companion.TranslationScreen())
    }

    fun onTranslateTabSelected() {
        router.navigateTo(Screens.Companion.TranslationScreen())
    }

    fun onHistoryTabSelected() {
        router.navigateTo(Screens.Companion.HistoryScreen())
    }

    fun onOptionsTabSelected() {
        router.navigateTo(Screens.Companion.OptionsScreen())
    }

    fun onBackPressed() {
        viewState.onFragmentExit()
    }
}