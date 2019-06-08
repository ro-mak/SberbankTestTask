package ru.makproductions.sberbanktesttask.presenter.history

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.makproductions.sberbanktesttask.model.repo.history.IHistoryRepo
import ru.makproductions.sberbanktesttask.view.history.HistoryView
import timber.log.Timber

@InjectViewState
open class HistoryPresenter(val scheduler: Scheduler) : MvpPresenter<HistoryView>(), KoinComponent {

    val historyRepo: IHistoryRepo by inject()
    val disposables: MutableList<Disposable> = mutableListOf()

    fun onCreate() {
        loadHistory()
    }

    fun loadHistory() {
        disposables.add(historyRepo.loadHistory().observeOn(scheduler).subscribe({ list ->

        }, { Timber.e(it) }))
    }

}