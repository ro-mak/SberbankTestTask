package ru.makproductions.sberbanktesttask.presenter.translate

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.makproductions.sberbanktesttask.common.toMap
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit
import ru.makproductions.sberbanktesttask.model.repo.translation.ITranslationRepo
import ru.makproductions.sberbanktesttask.view.translate.TranslationView
import timber.log.Timber
import java.util.*

@InjectViewState
class TranslationPresenter(val scheduler: Scheduler) : MvpPresenter<TranslationView>(), KoinComponent {

    private val translationRepo: ITranslationRepo by inject()
    private val disposables: MutableList<Disposable> = mutableListOf()
    fun onCreate() {
        if (!translationRepo.areLanguagesSaved()) {
            loadLanguages()
        }
    }

    private fun loadLanguages() {
        disposables.add(translationRepo.loadLanguages("ru").observeOn(scheduler).subscribe({
            Timber.e("Loading languages")
            val languages = it.languages
            val languageMap = languages.toMap()
            viewState.setLanguages(languageMap.values.toList())
            translationRepo.saveLanguageMap(languageMap)
        }, { Timber.e(it) }))
    }

    fun afterOriginalTextChanged(originalText: String, firstLanguage: String, secondLanguage: String) {
        Timber.e("TextWatcher = " + originalText)
        if (originalText.length == 0) {
            Timber.e("clear text")
            viewState.clearTranslationText()
            return
        }
        loadTranslation(originalText, firstLanguage, secondLanguage)
    }

    private fun loadTranslation(originalText: String, firstLanguage: String, secondLanguage: String) {
        disposables.add(
            translationRepo.loadTranslation(
                originalText,
                firstLanguage,
                secondLanguage
            ).observeOn(scheduler).subscribe({ translationItem ->
                Timber.e("Loading translation for " + originalText)
                val textStringBuilder = StringBuilder()
                for (line in translationItem.text) {
                    textStringBuilder.append(line)
                }
                val translationText = textStringBuilder.toString()
                translationRepo.saveHistoryUnit(
                    HistoryUnit(
                        originalText,
                        translationText,
                        Calendar.getInstance().time,
                        firstLanguage,
                        secondLanguage
                    )
                )
                viewState.setTranslationText(translationText)
            }, { Timber.e(it) })
        )
    }

    override fun onDestroy() {
        Timber.e("onDestroy")
        super.onDestroy()
        for (disposable in disposables) {
            disposable.dispose()
        }
    }

    fun onViewStateRestored() {
        val languages = translationRepo.getSavedLanguages() as? MutableList<String> ?: mutableListOf()
        viewState.setLanguages(languages)
        val historyUnit = translationRepo.getSavedHistoryUnit()
        historyUnit?.let {
            val firstLanguage = it.languageOriginal
            val secondLanguage = it.languageTranslation
            setViewLanguagesValues(languages, firstLanguage, secondLanguage)
            viewState.setOriginalText(it.originalText)
            viewState.setTranslationText(it.translationText)
        }
    }

    fun onDirectionChangeButtonClick() {
        val languages = translationRepo.getSavedLanguages() as? MutableList<String> ?: mutableListOf()
        val historyUnit = translationRepo.getSavedHistoryUnit()
        historyUnit?.let {
            val firstLanguage = it.languageOriginal
            val secondLanguage = it.languageTranslation
            setViewLanguagesValues(languages, secondLanguage, firstLanguage)
            translationRepo.saveHistoryUnit(
                HistoryUnit(
                    it.translationText,
                    it.originalText,
                    Calendar.getInstance().time,
                    it.languageTranslation,
                    it.languageOriginal
                )
            )
            setReversedViewTextValues(it)
        }
    }

    private fun setReversedViewTextValues(historyUnit: HistoryUnit) {
        val translationText = historyUnit.translationText
        val originalText = historyUnit.originalText
        viewState.setOriginalText(translationText)
        viewState.setTranslationText(originalText)
    }

    private fun setViewLanguagesValues(
        languages: MutableList<String>,
        firstLanguage: String,
        secondLanguage: String
    ) {
        viewState.setFirstLanguage(languages.indexOf(firstLanguage))
        viewState.setSecondLanguage(languages.indexOf(secondLanguage))
    }
}