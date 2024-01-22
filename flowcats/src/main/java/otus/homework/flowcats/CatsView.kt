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

    private var textView: TextView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        textView = findViewById(R.id.fact_textView)
    }

    override fun populate(fact: Fact) {
        textView?.text = fact.text
    }

    fun showError(error: String) {
        textView?.text = error
    }
}

interface ICatsView {

    fun populate(fact: Fact)
}