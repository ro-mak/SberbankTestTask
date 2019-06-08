package ru.makproductions.sberbanktesttask.model.repo.translation

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.sberbanktesttask.model.entity.Languages
import ru.makproductions.sberbanktesttask.model.entity.Translation
import ru.makproductions.sberbanktesttask.model.network.ITranslationNetService

class TranslationRepo(val netService: ITranslationNetService) : ITranslationRepo {
    override fun loadTranslation(line: String): Single<Translation> {
        return netService.loadTranslation(line = line).subscribeOn(Schedulers.io())
    }

    private var translationText = ""
    override fun saveTranslationText(translationText: String) {
        this.translationText = translationText
    }

    override fun getSavedTranslationText(): String = translationText
    override fun loadLanguages(locale: String): Single<Languages> {
        return netService.loadLanguages(locale = locale).subscribeOn(Schedulers.io())
    }

    private var languageMap = mapOf<String, String>()
    override fun saveLanguageMap(languageMap: Map<String, String>) {
        this.languageMap = languageMap
    }

    private var originalText = ""
    override fun saveOriginalText(originalText: String) {
        this.originalText = originalText
    }

    override fun getSavedOriginalText(): String = originalText
}