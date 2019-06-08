package ru.makproductions.sberbanktesttask.model.repo.translation

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.sberbanktesttask.model.entity.Languages
import ru.makproductions.sberbanktesttask.model.entity.Translation
import ru.makproductions.sberbanktesttask.model.network.ITranslationNetService

class TranslationRepo(val netService: ITranslationNetService) : ITranslationRepo {
    private var translationText = ""
    private var originalText = ""
    private var firstLanguage: String = ""
    private var secondLanguage: String = ""
    private var languageMap = mapOf<String, String>()

    override fun areLanguagesSaved(): Boolean = !languageMap.isEmpty()

    override fun loadTranslation(line: String): Single<Translation> {
        return netService.loadTranslation(line = line).subscribeOn(Schedulers.io())
    }


    override fun saveTranslationText(translationText: String) {
        this.translationText = translationText
    }

    override fun getSavedTranslationText(): String = translationText
    override fun loadLanguages(locale: String): Single<Languages> {
        return netService.loadLanguages(locale = locale).subscribeOn(Schedulers.io())
    }


    override fun saveLanguageMap(languageMap: Map<String, String>) {
        this.languageMap = languageMap
    }

    override fun getSavedLanguages(): List<String> {
        return languageMap.values.toList()
    }


    override fun saveOriginalText(originalText: String) {
        this.originalText = originalText
    }

    override fun getSavedOriginalText(): String = originalText


    override fun getSavedFirstLanguage(): String = firstLanguage

    override fun getSavedSecondLanguage(): String = secondLanguage

    override fun saveFirstLanguage(languageName: String) {
        this.firstLanguage = languageName
    }

    override fun saveSecondLanguage(languageName: String) {
        this.secondLanguage = languageName
    }
}