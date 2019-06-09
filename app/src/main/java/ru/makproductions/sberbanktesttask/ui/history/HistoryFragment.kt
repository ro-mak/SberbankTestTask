package ru.makproductions.sberbanktesttask.ui.history

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.makproductions.sberbanktesttask.R
import ru.makproductions.sberbanktesttask.presenter.history.HistoryPresenter
import ru.makproductions.sberbanktesttask.ui.history.adapter.HistoryRVAdapter
import ru.makproductions.sberbanktesttask.view.history.HistoryView

class HistoryFragment : MvpAppCompatFragment(), HistoryView {

    @InjectPresenter
    lateinit var historyPresenter: HistoryPresenter

    @ProvidePresenter
    fun providePresenter(): HistoryPresenter {
        val presenter = HistoryPresenter(AndroidSchedulers.mainThread())
        return presenter
    }

    companion object {
        fun getInstance(): HistoryFragment {
            val fragment = HistoryFragment()
            return fragment
        }
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean = true

        override fun onQueryTextChange(query: String?): Boolean {
            query?.let { historyPresenter.onSearchQueryTextChange(it) }
            return true
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.history_menu, menu)
        val menuItem = menu?.findItem(R.id.history_search_view)
        val searchView: SearchView = menuItem?.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(onQueryTextListener)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        initRecycler(view)
        setHasOptionsMenu(true)
        historyPresenter.onCreate()
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            if (it.itemId == R.id.history_delete_button) {
                historyPresenter.deleteHistory()
                onItemsUpdated()
            }
        }
        return false
    }

    private fun initRecycler(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.history_recycler_view)
        recycler.layoutManager = LinearLayoutManager(this.context)
        recycler.adapter = HistoryRVAdapter(historyPresenter.historyListPresenter)
    }

    override fun onItemsUpdated() {
        val recycler = view?.findViewById<RecyclerView>(R.id.history_recycler_view)
        recycler?.adapter?.notifyDataSetChanged()
    }

}