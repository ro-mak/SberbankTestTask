package ru.makproductions.sberbanktesttask.presenter.history

import io.reactivex.Observer
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit
import ru.makproductions.sberbanktesttask.view.history.HistoryItemView

interface IHistoryListPresenter {
    fun bindView(view: HistoryItemView)

    fun getItemCount(): Int

    fun setItemList(itemList: List<HistoryUnit>)
    fun deleteHistory()
    fun filterHistory(query: String)
    fun getClickSubject(position: Int): Observer<HistoryItemView>
}