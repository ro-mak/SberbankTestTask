package ru.makproductions.sberbanktesttask.presenter.translate

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import ru.makproductions.sberbanktesttask.view.translate.TranslationView

@InjectViewState
class TranslationPresenter(val scheduler: Scheduler) : MvpPresenter<TranslationView>() {
    fun onCreate() {

    }

    fun afterOriginalTextChanged(originalText: String) {
        viewState.setTranslationText(originalText)
    }

}