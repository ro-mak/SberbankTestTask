package ru.makproductions.sberbanktesttask.model.repo.translation

import io.reactivex.Single
import ru.makproductions.sberbanktesttask.model.entity.Languages
import ru.makproductions.sberbanktesttask.model.entity.Translation

interface ITranslationRepo {
    fun loadTranslation(line: String): Single<Translation>
    fun saveTranslationText(translationText: String)
    fun getSavedOriginalText(): String
    fun getSavedTranslationText(): String
    fun saveOriginalText(originalText: String)
    fun loadLanguages(locale: String): Single<Languages>
    fun saveLanguageMap(languageMap: Map<String, String>)
    fun getSavedLanguages(): List<String>
    fun getSavedFirstLanguage(): String
    fun getSavedSecondLanguage(): String
    fun saveFirstLanguage(languageName: String)
    fun saveSecondLanguage(languageName: String)
    fun areLanguagesSaved(): Boolean
}