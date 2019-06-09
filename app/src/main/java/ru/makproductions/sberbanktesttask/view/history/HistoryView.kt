package ru.makproductions.sberbanktesttask.view.history

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HistoryView : MvpView {
    fun onItemsUpdated()
}