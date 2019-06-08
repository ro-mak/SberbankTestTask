package ru.makproductions.sberbanktesttask.presenter.main

import com.arellomobile.mvp.MvpPresenter
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.makproductions.sberbanktesttask.view.main.MainView
import ru.terrakok.cicerone.Router

class MainPresenter : MvpPresenter<MainView>(), KoinComponent {
    val router: Router by inject()
    fun onCreate() {
        //  router.navigateTo(Screens.Companion.TranslationScreen())
    }
}