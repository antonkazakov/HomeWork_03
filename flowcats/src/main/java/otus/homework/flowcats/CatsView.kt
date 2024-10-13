package otus.homework.flowcats

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialTextView(context, attrs, defStyleAttr), ICatsView {

    override fun populate(text: String) {
        setText(text)
    }
    override fun showPrimaryColor() {
        setTextColor(Color.BLACK)
    }

    override fun showLoadingColor() {
        setTextColor(Color.GREEN)
    }

    override fun showErrorColor() {
        setTextColor(Color.RED)
    }
}

interface ICatsUiState {

    fun show(catsView: ICatsView)

    data class Success(private val text: String) : ICatsUiState {
        override fun show(catsView: ICatsView) {
            catsView.populate(text)
            catsView.showPrimaryColor()
        }
    }

    data class Loading(private val text: String) : ICatsUiState {
        override fun show(catsView: ICatsView) {
            catsView.populate(text)
            catsView.showLoadingColor()
        }
    }

    data class Error(private val message: String) : ICatsUiState {
        override fun show(catsView: ICatsView) {
            catsView.populate(message)
            catsView.showErrorColor()
        }
    }
}

interface ICatsView {
    fun populate(text: String)
    fun showPrimaryColor()
    fun showLoadingColor()
    fun showErrorColor()
}