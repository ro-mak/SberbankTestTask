package ru.makproductions.sberbanktesttask.presenter.history

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit
import ru.makproductions.sberbanktesttask.model.repo.history.IHistoryRepo
import ru.makproductions.sberbanktesttask.navigation.Screens
import ru.makproductions.sberbanktesttask.view.history.HistoryItemView
import ru.makproductions.sberbanktesttask.view.history.HistoryView
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
open class HistoryPresenter(val scheduler: Scheduler) : MvpPresenter<HistoryView>(), KoinComponent {

    val historyRepo: IHistoryRepo by inject()
    val router: Router by inject()
    val disposables: MutableList<Disposable> = mutableListOf()
    val historyListPresenter: IHistoryListPresenter = HistoryListPresenter()
    fun onCreate() {
        loadHistory()
    }

    fun loadHistory() {
        Timber.e("loadHistory")
        disposables.add(historyRepo.loadHistory().observeOn(scheduler).subscribe({ list ->
            historyListPresenter.setItemList(list)
            historyRepo.saveHistory(list)
        }, { Timber.e(it) }))
    }

    fun deleteHistory() {
        historyRepo.deleteHistory()
        historyListPresenter.deleteHistory()
    }

    fun onSearchQueryTextChange(query: String) {
        historyListPresenter.filterHistory(query)
    }

    inner class HistoryListPresenter : IHistoryListPresenter {
        private var itemList: MutableList<HistoryUnit> = mutableListOf()
        private var clickSubjects = ArrayList<PublishSubject<HistoryItemView>>()
        private fun createPublishSubjects() {
            clickSubjects.clear()
            val elementsToUpdate = getItemCount()
            for (i in 0 until elementsToUpdate) {
                clickSubjects.add(PublishSubject.create<HistoryItemView>())
            }
        }

        override fun bindView(view: HistoryItemView) {
            view.pos?.let {
                val historyUnit = itemList[it]
                view.setOriginalText(historyUnit.originalText)
                view.setTranslationText(historyUnit.translationText)
                disposables.add(
                    clickSubjects.get(it).subscribe(
                        { clickSubjectsNewsItemView ->
                            router.navigateTo(Screens.Companion.TranslationScreen(historyUnit))
                        },
                        { Timber.e(it) })
                )
            }
        }

        override fun filterHistory(query: String) {
            val filteredList: MutableList<HistoryUnit> = mutableListOf()
            for (item in historyRepo.getSavedHistory()) {
                if (item.originalText.startsWith(query) || item.translationText.startsWith(query)) {
                    filteredList.add(item)
                }
            }
            setItemList(filteredList)
        }

        override fun deleteHistory() {
            itemList.clear()
        }

        override fun getClickSubject(position: Int): Observer<HistoryItemView> {
            return clickSubjects.get(position)
        }

        override fun getItemCount() = itemList.size

        override fun setItemList(itemList: List<HistoryUnit>) {
            this.itemList = itemList.toMutableList()
            createPublishSubjects()
            viewState.onItemsUpdated()
        }
    }

    override fun onDestroy() {
        Timber.e("OnDestroy")
        super.onDestroy()
        for (disposable in disposables) {
            disposable.dispose()
        }
    }
}