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

    override fun populate(fact: Fact) {
        findViewById<TextView>(R.id.fact_textView).text = fact.text
    }

    override fun setLoadingVisible(visible: Boolean) {
        findViewById<ProgressBar>(R.id.loading).visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

interface ICatsView {

    fun populate(fact: Fact)
    fun setLoadingVisible(visible: Boolean)
}