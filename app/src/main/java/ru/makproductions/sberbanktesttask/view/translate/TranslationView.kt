package ru.makproductions.sberbanktesttask.view.translate

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TranslationView : MvpView {
    fun setTranslationText(translationText: String)
}