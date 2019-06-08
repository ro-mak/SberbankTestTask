package ru.makproductions.sberbanktesttask.model.repo

import io.reactivex.Single
import ru.makproductions.sberbanktesttask.model.entity.IHistoryUnit

interface IHistoryRepo {
    fun loadHistory(): Single<List<IHistoryUnit>>
}