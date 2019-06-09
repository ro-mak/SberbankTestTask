package ru.makproductions.sberbanktesttask.di.modules

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router


private val cicerone = Cicerone.create()


fun cicerone(): Cicerone<Router> {
    return cicerone
}

fun navigatorHolder(): NavigatorHolder {
    return cicerone.navigatorHolder
}

fun router(): Router {
    return cicerone.router
}
