package ru.makproductions.sberbanktesttask.ui.translate

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
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
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_translation.*
import ru.makproductions.sberbanktesttask.R
import ru.makproductions.sberbanktesttask.common.toArray
import ru.makproductions.sberbanktesttask.common.toHistoryUnit
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit
import ru.makproductions.sberbanktesttask.presenter.translate.TranslationPresenter
import ru.makproductions.sberbanktesttask.view.translate.TranslationView
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TranslationFragment : MvpAppCompatFragment(), TranslationView {

    @InjectPresenter
    lateinit var presenter: TranslationPresenter


    @ProvidePresenter
    fun providePresenter(): TranslationPresenter {
        val presenter = TranslationPresenter(AndroidSchedulers.mainThread())
        return presenter
    }

    companion object {
        private val HISTORY_UNIT = "HISTORY_UNIT"
        fun getInstance(historyUnit: HistoryUnit? = null): TranslationFragment {
            val fragment = TranslationFragment()
            historyUnit?.let {
                val bundle = Bundle()
                bundle.putStringArray(HISTORY_UNIT, it.toArray())
                fragment.arguments = bundle
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isFirstLoad = true
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
        original_text_input_edit_text.removeTextChangedListener(originalTextWatcher)
        original_text_input_edit_text.setText(originalText)
        original_text_input_edit_text.addTextChangedListener(originalTextWatcher)
    }

    override fun onResume() {
        Timber.e("onResume")
        isFirstLoad = true
        super.onResume()
        val args = arguments
        args?.let {
            Timber.e("with Bundle")
            presenter.OnArgumentsReceived(it.getStringArray(HISTORY_UNIT)?.toHistoryUnit())
        }
        arguments = null
        presenter.onViewStateRestored()


        showToolbar()
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigation?.menu?.findItem(R.id.translate_tab)?.isChecked = true
    }

    override fun onAttach(context: Context?) {
        Timber.e("onAttach")
        super.onAttach(context)

    }

    private fun showToolbar() {
        setToolbarVisibitity(View.VISIBLE)
    }

    override fun onPause() {
        Timber.e("onPause")
        super.onPause()
        hideToolBar()
        disposable?.dispose()
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
    private var publishSubject: PublishSubject<Editable?> = PublishSubject.create()
    private var disposable: Disposable? = null

    private val originalTextWatcher = object : TextWatcher {
        init {
            Timber.e("Textwatcher init")
        }

        override fun afterTextChanged(changedEditable: Editable?) {
            Timber.e("text changed first?=" + isFirstLoad)
            if (!isFirstLoad) {
                changedEditable?.let { publishSubject.onNext(it) }
            }

            isFirstLoad = false
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }


    private fun initInputFields(view: View) {
        disposable = publishSubject.debounce(1000, TimeUnit.MILLISECONDS)
            .switchMap(Function<Editable?, ObservableSource<String>> { editable ->
                Observable.create { emitter ->
                    emitter.onNext(
                        editable.toString()
                    )
                }
            })
            .subscribe({
                val toolbar = activity?.findViewById<Toolbar>(R.id.main_toolbar)
                toolbar?.let { innerToolbar ->
                    presenter.afterOriginalTextChanged(
                        it,
                        innerToolbar.getSpinnerSelectedItem(R.id.first_language_spinner),
                        innerToolbar.getSpinnerSelectedItem(R.id.second_language_spinner)
                    )
                }
            }, { Timber.e(it) })
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