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
        findViewById<TextView>(R.id.fact_textView).text = fact.fact
    }

    override fun progressOn() {
        findViewById<ProgressBar>(R.id.progress).visibility = VISIBLE
    }

    override fun progressOff() {
        findViewById<ProgressBar>(R.id.progress).visibility = INVISIBLE
    }
}

interface ICatsView {

    fun populate(fact: Fact)

    fun progressOn()

    fun progressOff()
}