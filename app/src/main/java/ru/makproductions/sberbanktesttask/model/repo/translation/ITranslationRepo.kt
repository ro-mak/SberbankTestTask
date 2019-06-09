package ru.makproductions.sberbanktesttask.model.repo.translation

import io.reactivex.Single
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit
import ru.makproductions.sberbanktesttask.model.entity.Languages
import ru.makproductions.sberbanktesttask.model.entity.Translation

interface ITranslationRepo {
    fun loadTranslation(line: String, firstLanguage: String, secondLanguage: String): Single<Translation>
    fun getSavedOriginalText(): String
    fun getSavedTranslationText(): String
    fun loadLanguages(locale: String): Single<Languages>
    fun saveLanguageMap(languageMap: Map<String, String>)
    fun getSavedLanguages(): List<String>
    fun getSavedFirstLanguage(): String
    fun getSavedSecondLanguage(): String
    fun areLanguagesSaved(): Boolean
    fun saveHistoryUnit(historyUnit: HistoryUnit, isCached: Boolean)
    fun getSavedHistoryUnit(): HistoryUnit?
    fun getDefaultFirstLanguage(): String
    fun getDefaultSecondLanguage(): String
}