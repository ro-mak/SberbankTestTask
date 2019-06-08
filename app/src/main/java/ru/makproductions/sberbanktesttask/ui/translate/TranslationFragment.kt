package ru.makproductions.sberbanktesttask.ui.translate

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_translation.*
import ru.makproductions.sberbanktesttask.R
import ru.makproductions.sberbanktesttask.presenter.translate.TranslationPresenter
import ru.makproductions.sberbanktesttask.view.translate.TranslationView
import timber.log.Timber

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
        Timber.e("onCreateView")
        val view = inflater.inflate(R.layout.fragment_translation, container, false)
        initInputFields(view)
        presenter.onCreate()
        return view
    }

    override fun setLangDirection(translationDirection: String) {
        val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar?.let {
            val directions = translationDirection.split("-")
            val originalDirectionTextView = it.findViewById<TextView>(R.id.first_language_text_view)
            originalDirectionTextView?.text = directions[0]
            val translationDirectionTextView = it.findViewById<TextView>(R.id.second_language_text_view)
            translationDirectionTextView.text = directions[1]
        }
    }

    override fun setOriginalText(originalText: String) {
        Timber.e("Set original to " + originalText)
        original_text_input_edit_text.setText(originalText)
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewStateRestored()
    }

    override fun onPause() {
        Timber.e("onPause")
        super.onPause()
        presenter.onSaveOriginalText(original_text_input_edit_text?.text.toString())
        presenter.onSaveTranslationText(translation_text_input_edit_text?.text.toString())
    }

    private fun initInputFields(view: View) {
        val originalTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Timber.e("change text = " + s.toString())
                presenter.afterOriginalTextChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        }
        view.findViewById<TextInputEditText>(R.id.original_text_input_edit_text)
            .addTextChangedListener(originalTextWatcher)
    }

    override fun setTranslationText(translationText: String) {
        original_text_input_edit_text.text?.let {
            if (it.isNotEmpty()) translation_text_input_edit_text.setText(translationText)
        }

    }

    override fun clearTranslationText() {
        translation_text_input_edit_text.setText("")
    }
}