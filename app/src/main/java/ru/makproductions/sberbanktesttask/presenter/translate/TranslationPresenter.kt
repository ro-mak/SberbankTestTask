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
    private var translationText = ""
    private var originalText = ""
    fun onCreate() {}

    fun afterOriginalTextChanged(originalText: String) {
        Timber.e("text = " + originalText)
        if (originalText.length == 0) {
            Timber.e("clear text")
            viewState.clearTranslationText()
            return
        }
        this.originalText = originalText
        disposables.add(translationRepo.loadTranslation(originalText).observeOn(scheduler).subscribe({ translationItem ->
            val textStringBuilder = StringBuilder()
            for (line in translationItem.text) {
                textStringBuilder.append(line)
            }
            translationText = textStringBuilder.toString()
            viewState.setTranslationText(translationText)
            viewState.setLangDirection(translationItem.translationDirection)
        }, { Timber.e(it) }))
    }

    override fun onDestroy() {
        super.onDestroy()
        for (disposable in disposables) {
            disposable.dispose()
        }
    }

    fun onSaveTranslationText(translationText: String) {
        this.translationText = translationText
    }

    fun onSaveOriginalText(originalText: String) {
        this.originalText = originalText
    }
}