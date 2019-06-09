package ru.makproductions.sberbanktesttask.ui.translate

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
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

    val onDirectionChangeButtonClickListener = View.OnClickListener { presenter.onDirectionChangeButtonClick() }

    override fun setLanguages(languageList: List<String>) {
        val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar?.let {
            val firstLanguageSpinner = it.findViewById<Spinner>(R.id.first_language_spinner)
            val firstLanguageSpinnerAdapter = ArrayAdapter<String>(context!!, R.layout.language_spinner_item)
            firstLanguageSpinnerAdapter.addAll(languageList)
            firstLanguageSpinner.adapter = firstLanguageSpinnerAdapter
            val secondLanguageSpinner = it.findViewById<Spinner>(R.id.second_language_spinner)
            val secondLanguageSpinnerAdapter = ArrayAdapter<String>(context!!, R.layout.language_spinner_item)
            secondLanguageSpinnerAdapter.addAll(languageList)
            secondLanguageSpinner.adapter = secondLanguageSpinnerAdapter
            val directionChangeButton = it.findViewById<ImageView>(R.id.change_translation_direction_button)
            directionChangeButton.setOnClickListener(onDirectionChangeButtonClickListener)
        }
    }

    override fun setOriginalText(originalText: String) {
        Timber.e("Set original to " + originalText)
        original_text_input_edit_text.setText(originalText)
    }

    override fun onResume() {
        Timber.e("onResume")
        super.onResume()
        presenter.onViewStateRestored()
        showToolbar()
    }

    private fun showToolbar() {
        setToolbarVisibitity(View.VISIBLE)
    }

    override fun onPause() {
        Timber.e("onPause")
        super.onPause()
        hideToolBar()
    }

    override fun setFirstLanguage(position: Int) {
        val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
        Timber.e("set first to " + position)
        toolbar?.let {
            val firstLanguageSpinner = it.findViewById<Spinner>(R.id.first_language_spinner)
            firstLanguageSpinner.setSelection(position)
        }
    }

    override fun setSecondLanguage(position: Int) {
        val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
        Timber.e("set second to " + position)
        toolbar?.let {
            val secondLanguageSpinner = it.findViewById<Spinner>(R.id.second_language_spinner)
            secondLanguageSpinner.setSelection(position)
        }
    }

    private fun hideToolBar() {
        setToolbarVisibitity(View.GONE)
    }

    private fun setToolbarVisibitity(visibility: Int) {
        val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar?.let {
            it.findViewById<Spinner>(R.id.first_language_spinner).visibility = visibility
            it.findViewById<Spinner>(R.id.second_language_spinner).visibility = visibility
            it.findViewById<ImageView>(R.id.change_translation_direction_button).visibility = visibility
        }
    }

    private fun Toolbar.getSpinnerSelectedItem(resId: Int): String {
        return this.findViewById<Spinner>(resId).selectedItem as? String ?: ""
    }

    private var isFirstLoad = true
    private fun initInputFields(view: View) {
        val originalTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isFirstLoad) {
                    Timber.e("change text = " + s.toString())
                    val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
                    toolbar?.let {
                        presenter.afterOriginalTextChanged(
                            s.toString(),
                            it.getSpinnerSelectedItem(R.id.first_language_spinner),
                            it.getSpinnerSelectedItem(R.id.second_language_spinner)
                        )
                    }
                }
                isFirstLoad = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        }
        val originalInputField = view.findViewById<TextInputEditText>(R.id.original_text_input_edit_text)
        originalInputField.addTextChangedListener(originalTextWatcher)
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