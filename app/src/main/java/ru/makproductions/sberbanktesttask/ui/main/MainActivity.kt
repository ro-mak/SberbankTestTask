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
        presenter.onCreate()
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
