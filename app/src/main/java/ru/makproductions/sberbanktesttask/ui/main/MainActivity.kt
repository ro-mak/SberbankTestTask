package ru.makproductions.sberbanktesttask.ui.main

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.makproductions.sberbanktesttask.R
import ru.makproductions.sberbanktesttask.navigation.FragmentNavigator
import ru.makproductions.sberbanktesttask.navigation.Screens
import ru.makproductions.sberbanktesttask.presenter.main.MainPresenter
import ru.makproductions.sberbanktesttask.ui.translate.TranslationFragment
import ru.makproductions.sberbanktesttask.view.main.MainView
import ru.terrakok.cicerone.NavigatorHolder
import timber.log.Timber

class MainActivity : MvpAppCompatActivity(), MainView, KoinComponent {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    val navigatorHolder: NavigatorHolder by inject()

    private val navigator = FragmentNavigator(this, R.id.fragment_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        initBottomNavigation()
        presenter.onCreate()
    }

    private fun initBottomNavigation() {
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.translate_tab -> presenter.onTranslateTabSelected().let { true }
                R.id.history_tab -> presenter.onHistoryTabSelected().let { true }
                R.id.options_tab -> presenter.onOptionsTabSelected().let { true }
                else -> false
            }
        }
    }

    override fun onFragmentExit() {
        val fragment = supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 2).name
        Timber.e("${fragment}")
        if (fragment == Screens.Companion.TranslationScreen::class.java.canonicalName) {
            bottom_navigation_view.selectedItemId = R.id.translate_tab
        } else if (fragment == Screens.Companion.HistoryScreen::class.java.canonicalName) {
            bottom_navigation_view.selectedItemId = R.id.history_tab
        } else if (fragment == Screens.Companion.OptionsScreen::class.java.canonicalName) {
            bottom_navigation_view.selectedItemId = R.id.options_tab
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is TranslationFragment) {
            finish()
        } else {
            presenter.onBackPressed()
        }
    }

    @ProvidePresenter
    fun providePresenter() = MainPresenter()


    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

}
