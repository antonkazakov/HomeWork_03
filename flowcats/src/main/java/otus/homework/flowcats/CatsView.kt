package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    private lateinit var textView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onFinishInflate() {
        super.onFinishInflate()
        textView = findViewById(R.id.fact_textView)
        progressBar = findViewById(R.id.fact_progressBar)
    }

    override fun handleNewUiState(result: Result) {
        when(result) {
            is Error -> {
                progressBar.visibility = View.GONE
                val errorMessage = result.throwable.message ?: "Smt bad happened!"
                textView.text = errorMessage
            }
            is Success<FactsUiState> -> {
                when(result.model) {
                    Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is Signature -> {
                        progressBar.visibility = View.GONE
                        textView.text = result.model.text
                    }
                }
            }
        }
    }
}

interface ICatsView {

    fun handleNewUiState(result: Result)
}