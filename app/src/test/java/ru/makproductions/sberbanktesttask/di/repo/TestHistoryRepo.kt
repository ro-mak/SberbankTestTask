package ru.makproductions.sberbanktesttask.di.repo

import io.reactivex.Single
import org.mockito.Mockito
import ru.makproductions.sberbanktesttask.model.repo.history.IHistoryRepo

fun getTestHistoryRepo(): IHistoryRepo {
    val historyRepo = Mockito.mock(IHistoryRepo::class.java)
    Mockito.`when`(historyRepo.loadHistory()).thenReturn(Single.just(listOf()))
    return historyRepo
}
