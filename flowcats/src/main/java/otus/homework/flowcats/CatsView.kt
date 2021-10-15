package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {
    private val textView by lazy {
        findViewById<TextView>(R.id.fact_textView)
    }

    override fun populate(result: Result<CatsViewState>) = when (result) {
        is Success<CatsViewState> -> textView.text = result.state.catsFact.text
        is Error -> textView.text = result.message
        is Loading -> textView.text = resources.getText(R.string.loading_text)
    }
}

interface ICatsView {

    fun populate(result: Result<CatsViewState>)
}