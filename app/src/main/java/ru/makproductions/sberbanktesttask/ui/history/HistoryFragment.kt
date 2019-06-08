package ru.makproductions.sberbanktesttask.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.makproductions.sberbanktesttask.R
import ru.makproductions.sberbanktesttask.view.history.HistoryView

class HistoryFragment : MvpAppCompatFragment(), HistoryView {
    companion object {
        fun getInstance(): HistoryFragment {
            val fragment = HistoryFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        return view
    }
}