package ru.makproductions.sberbanktesttask

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.makproductions.sberbanktesttask.di.cacheModule
import ru.makproductions.sberbanktesttask.di.ciceroneModule
import ru.makproductions.sberbanktesttask.di.networkModule
import ru.makproductions.sberbanktesttask.di.repoModule
import ru.makproductions.sberbanktesttask.model.room.db.HistoryDatabase
import timber.log.Timber

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
        HistoryDatabase.create(this)
        startKoin {
            androidContext(this@App)
            modules(listOf(repoModule, cacheModule, ciceroneModule, networkModule))
        }
    }
}