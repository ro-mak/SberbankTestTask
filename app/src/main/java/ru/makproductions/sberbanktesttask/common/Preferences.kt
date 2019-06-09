package ru.makproductions.sberbanktesttask.common

import android.content.Context
import android.content.SharedPreferences
import ru.makproductions.sberbanktesttask.App
import ru.makproductions.sberbanktesttask.R

object Prefs {
    private val MAIN_SHARED_PREFERENCES = "main"
    private val THEME_PREFS_NAME = "theme"
    internal var sharedPreferences: SharedPreferences
    val theme: Int
        get() = sharedPreferences.getInt(THEME_PREFS_NAME, R.style.BlueTheme)

    init {
        sharedPreferences = App.instance.getSharedPreferences(MAIN_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun setThemeId(id: Int) {
        sharedPreferences.edit().putInt(THEME_PREFS_NAME, id).apply()
    }

}