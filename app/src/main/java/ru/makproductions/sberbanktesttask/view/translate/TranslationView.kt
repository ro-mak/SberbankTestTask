package ru.makproductions.sberbanktesttask.view.translate

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface TranslationView : MvpView {
    fun setTranslationText(translationText: String)
    fun clearTranslationText()
    fun setOriginalText(originalText: String)
    fun setLanguages(languageList: List<String>)
    fun setFirstLanguage(position: Int)
    fun setSecondLanguage(position: Int)
}