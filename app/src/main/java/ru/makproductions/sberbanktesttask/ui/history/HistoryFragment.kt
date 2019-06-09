package ru.makproductions.sberbanktesttask.ui.history

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        toolbar?.findViewById<ImageView>(R.id.history_delete_button)?.visibility = View.VISIBLE
        toolbar?.findViewById<SearchView>(R.id.history_search_view)?.visibility = View.VISIBLE


    }

    private fun hideHistoryToolbarViews() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar?.findViewById<ImageView>(R.id.history_delete_button)?.visibility = View.GONE
        toolbar?.findViewById<SearchView>(R.id.history_search_view)?.visibility = View.GONE
    }


    override fun onPause() {
        super.onPause()
        hideHistoryToolbarViews()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        initRecycler(view)
        initDeleteButton()
        historyPresenter.onCreate()
        return view
    }

    private fun initDeleteButton() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
        val deleteButtonView = toolbar?.findViewById<ImageView>(R.id.history_delete_button)
        deleteButtonView?.setOnClickListener({
            historyPresenter.deleteHistory()
            onItemsUpdated()
        })
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