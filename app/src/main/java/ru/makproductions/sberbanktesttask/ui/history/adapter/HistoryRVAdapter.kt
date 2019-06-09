package ru.makproductions.sberbanktesttask.ui.history.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.makproductions.sberbanktesttask.R
import ru.makproductions.sberbanktesttask.presenter.history.IHistoryListPresenter
import ru.makproductions.sberbanktesttask.view.history.HistoryItemView

class HistoryRVAdapter(val listPresenter: IHistoryListPresenter) :
    RecyclerView.Adapter<HistoryRVAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int) = HistoryViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.history_recycler_view_item, parent, false
        )
    )

    override fun getItemCount(): Int = listPresenter.getItemCount()


    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.pos = position
        listPresenter.bindView(holder)
    }

    inner class HistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view), HistoryItemView {
        override var pos: Int? = null
        override fun setTranslationText(translationText: String) {
            view.findViewById<TextView>(R.id.history_translation_text_view).text = translationText
        }

        override fun setOriginalText(originalText: String) {
            view.findViewById<TextView>(R.id.history_original_text_view).text = originalText
        }
    }
}