package ru.makproductions.sberbanktesttask.presenter.translate

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.makproductions.sberbanktesttask.model.repo.translation.ITranslationRepo
import ru.makproductions.sberbanktesttask.view.translate.TranslationView
import timber.log.Timber

@InjectViewState
class TranslationPresenter(val scheduler: Scheduler) : MvpPresenter<TranslationView>(), KoinComponent {

    private val translationRepo: ITranslationRepo by inject()
    private val disposables: MutableList<Disposable> = mutableListOf()
    fun onCreate() {}

    fun afterOriginalTextChanged(originalText: String) {
        Timber.e("text = " + originalText)
        if (originalText.length == 0) {
            Timber.e("clear text")
            viewState.clearTranslationText()
            return
        }
        disposables.add(translationRepo.loadTranslation(originalText).observeOn(scheduler).subscribe({ translationItem ->
            Timber.e("Loading translation for " + originalText)
            val textStringBuilder = StringBuilder()
            for (line in translationItem.text) {
                textStringBuilder.append(line)
            }

            viewState.setTranslationText(textStringBuilder.toString())
            viewState.setLangDirection(translationItem.translationDirection)
        }, { Timber.e(it) }))
    }

    override fun onDestroy() {
        Timber.e("onDestroy")
        super.onDestroy()
        for (disposable in disposables) {
            disposable.dispose()
        }
    }

    fun onSaveTranslationText(translationText: String) {
        translationRepo.saveTranslationText(translationText)
    }

    fun onSaveOriginalText(originalText: String) {
        translationRepo.saveOriginalText(originalText)
    }

    fun onViewStateRestored() {
        viewState.setOriginalText(translationRepo.getSavedOriginalText())
        viewState.setTranslationText(translationRepo.getSavedTranslationText())
    }
}