package ru.makproductions.sberbanktesttask.presenter.history

import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.makproductions.sberbanktesttask.di.testRepoModule
import ru.makproductions.sberbanktesttask.model.repo.history.IHistoryRepo

class HistoryPresenterTest : KoinTest {

    private lateinit var historyPresenter: HistoryPresenter
    private lateinit var testScheduler: TestScheduler

    private val repo: IHistoryRepo by inject()

    @Before
    fun setUp() {
        startKoin { testRepoModule }
        loadKoinModules(testRepoModule)
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        historyPresenter = Mockito.spy(HistoryPresenter(testScheduler))
    }

    @Test
    fun onCreate() {
        historyPresenter.onCreate()
        Mockito.verify(historyPresenter).loadHistory()
    }

    @Test
    fun loadHistory() {
        historyPresenter.loadHistory()
        Mockito.verify(repo).loadHistory()
    }

    @After
    fun onTearDown() {
        stopKoin()
    }

}