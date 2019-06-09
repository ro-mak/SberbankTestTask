package ru.makproductions.sberbanktesttask.presenter.history

import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit
import ru.makproductions.sberbanktesttask.view.history.HistoryItemView

interface IHistoryListPresenter {
    fun bindView(view: HistoryItemView)

    fun getItemCount(): Int

    fun setItemList(itemList: List<HistoryUnit>)
    fun deleteHistory()
}