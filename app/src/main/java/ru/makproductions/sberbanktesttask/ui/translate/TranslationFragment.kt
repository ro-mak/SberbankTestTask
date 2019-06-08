package ru.makproductions.sberbanktesttask.ui.translate

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_translation.*
import ru.makproductions.sberbanktesttask.R
import ru.makproductions.sberbanktesttask.presenter.translate.TranslationPresenter
import ru.makproductions.sberbanktesttask.view.translate.TranslationView

class TranslationFragment : MvpAppCompatFragment(), TranslationView {

    @InjectPresenter
    lateinit var presenter: TranslationPresenter

    @ProvidePresenter
    fun providePresenter(): TranslationPresenter {
        val presenter = TranslationPresenter(AndroidSchedulers.mainThread())
        return presenter
    }

    companion object {
        fun getInstance(): TranslationFragment {
            val fragment = TranslationFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_translation, container, false)
        presenter.onCreate()
        initInputFields(view)
        return view
    }

    private fun initInputFields(view: View) {
        val originalTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.afterOriginalTextChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        }
        view.findViewById<TextInputEditText>(R.id.original_text_input_edit_text)
            .addTextChangedListener(originalTextWatcher)
    }

    override fun setTranslationText(translationText: String) {
        translation_text_input_edit_text.setText(translationText)
    }
}