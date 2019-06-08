package ru.makproductions.sberbanktesttask.ui.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.makproductions.sberbanktesttask.R

class TranslationFragment : MvpAppCompatFragment() {
    companion object {
        fun getInstance(): TranslationFragment {
            val fragment = TranslationFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_translation, container, false)
        return view
    }
}