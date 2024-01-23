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

    override fun populate(uiState: CatsUiState<Fact>) {
        findViewById<TextView>(R.id.fact_textView).text = when (uiState) {
            is CatsUiState.Common -> uiState.data.text
            is CatsUiState.Error -> uiState.message
            is CatsUiState.Loading -> "Loading..."
        }
    }
}

interface ICatsView {

    fun populate(uiState: CatsUiState<Fact>)
}