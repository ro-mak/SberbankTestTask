package ru.makproductions.sberbanktesttask.model.repo.translation

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.sberbanktesttask.model.cache.ICache
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit
import ru.makproductions.sberbanktesttask.model.entity.Languages
import ru.makproductions.sberbanktesttask.model.entity.Translation
import ru.makproductions.sberbanktesttask.model.network.ITranslationNetService
import timber.log.Timber

class TranslationRepo(val netService: ITranslationNetService, val cache: ICache) : ITranslationRepo {

    private var languageMap = mapOf<String, String>()
    private var historyUnit: HistoryUnit? = null

    override fun saveHistoryUnit(historyUnit: HistoryUnit, isCached: Boolean) {
        this.historyUnit = historyUnit
        if (isCached) cache.saveHistoryUnit(historyUnit)
    }

    override fun getSavedHistoryUnit(): HistoryUnit? {
        return historyUnit
    }

    override fun areLanguagesSaved(): Boolean = !languageMap.isEmpty()

    override fun loadTranslation(line: String, firstLanguage: String, secondLanguage: String): Single<Translation> {
        var direction = getLanguageDirection(firstLanguage, secondLanguage)
        Timber.e("direction = " + direction)
        if (direction.isEmpty()) direction = "en-ru"
        return netService.loadTranslation(line = line, translationDirection = direction).subscribeOn(Schedulers.io())
    }

    override fun getSavedTranslationText(): String = historyUnit?.translationText ?: ""
    override fun loadLanguages(locale: String): Single<Languages> {
        return netService.loadLanguages(locale = locale).subscribeOn(Schedulers.io())
    }


    override fun saveLanguageMap(languageMap: Map<String, String>) {
        this.languageMap = languageMap
    }

    override fun getSavedLanguages(): List<String> {
        return languageMap.values.toList()
    }

    override fun getSavedOriginalText(): String = historyUnit?.originalText ?: ""


    override fun getSavedFirstLanguage(): String = historyUnit?.languageOriginal ?: ""

    override fun getSavedSecondLanguage(): String = historyUnit?.languageTranslation ?: ""

    private fun getLanguageDirection(firstLanguage: String, secondLanguage: String): String = StringBuilder().apply {
        if (firstLanguage.isEmpty() || secondLanguage.isEmpty()) return "en-ru"
        for (pair in languageMap) {
            if (pair.value == firstLanguage) {
                this.append(pair.key)
            }
        }
        this.append("-")
        for (pair in languageMap) {
            if (pair.value == secondLanguage) {
                this.append(pair.key)
            }
        }
    }.toString()
}