package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import otus.homework.flowcats.CatsViewModel.Result
import otus.homework.flowcats.CatsViewModel.Result.*

interface ICatsView {
    fun populate(result: Result)
}

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    private val factText by lazy { findViewById<TextView>(R.id.catFactText) }
    private val loadingProgressBar by lazy { findViewById<ProgressBar>(R.id.catProgressBar) }

    override fun populate(result: Result) = when (result) {
        is Success -> {
            loadingProgressBar.isVisible = false
            factText.text = result.fact.text
        }
        is Error -> {
            loadingProgressBar.isVisible = false
            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
        }
        Loading -> {
            loadingProgressBar.isVisible = true
        }
    }
}
