package ru.makproductions.sberbanktesttask.ui.history

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
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

    override fun onResume() {
        super.onResume()
        showHistoryToolbarViews()
    }

    private fun showHistoryToolbarViews() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar?.findViewById<SearchView>(R.id.history_search_view)?.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.history_menu, menu)
    }

    private fun hideHistoryToolbarViews() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar?.findViewById<SearchView>(R.id.history_search_view)?.visibility = View.GONE
    }


    override fun onPause() {
        super.onPause()
        hideHistoryToolbarViews()
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