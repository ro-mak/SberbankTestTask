package ru.makproductions.sberbanktesttask.di

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.makproductions.sberbanktesttask.di.repo.getTestHistoryRepo
import ru.makproductions.sberbanktesttask.model.repo.history.IHistoryRepo

val testRepoModule = module {
    single { getTestHistoryRepo() } bind IHistoryRepo::class
}