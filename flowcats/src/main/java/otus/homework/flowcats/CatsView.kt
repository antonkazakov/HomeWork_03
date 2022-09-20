package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(fact: Fact) {
        findViewById<ProgressBar>(R.id.progressBar).visibility = GONE
        with(findViewById<TextView>(R.id.fact_textView)) {
            text = fact.text
            visibility = VISIBLE
        }
    }

    fun populate(text: String) {
        findViewById<TextView>(R.id.fact_textView).text = text
    }

    fun showLoading() {
        findViewById<TextView>(R.id.fact_textView).visibility = GONE
        findViewById<ProgressBar>(R.id.progressBar).visibility = VISIBLE
    }

    fun showError(message: String) {
        findViewById<ProgressBar>(R.id.progressBar).visibility = GONE
        with(findViewById<TextView>(R.id.fact_textView)) {
            text = message
            visibility = VISIBLE
        }
    }
}

interface ICatsView {

    fun populate(fact: Fact)
}