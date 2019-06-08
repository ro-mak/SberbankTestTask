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
import ru.makproductions.sberbanktesttask.presenter.main.MainPresenter
import ru.makproductions.sberbanktesttask.ui.history.HistoryFragment
import ru.makproductions.sberbanktesttask.ui.options.OptionsFragment
import ru.makproductions.sberbanktesttask.ui.translate.TranslationFragment
import ru.makproductions.sberbanktesttask.view.main.MainView
import ru.terrakok.cicerone.NavigatorHolder

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
        if (savedInstanceState == null) {
            presenter.onCreate()
        }
    }

    private fun initBottomNavigation() {
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            when (it.itemId) {
                R.id.translate_tab ->
                    if (fragment is TranslationFragment)
                        true
                    else
                        presenter.onTranslateTabSelected().let { true }
                R.id.history_tab ->
                    if (fragment is HistoryFragment)
                        true
                    else
                        presenter.onHistoryTabSelected().let { true }
                R.id.options_tab ->
                    if (fragment is OptionsFragment)
                        true
                    else
                        presenter.onOptionsTabSelected().let { true }
                else -> false
            }
        }
    }

    override fun onFragmentExit() {
        bottom_navigation_view.selectedItemId = R.id.translate_tab
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
