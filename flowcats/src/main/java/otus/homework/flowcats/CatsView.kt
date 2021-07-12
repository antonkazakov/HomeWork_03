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

    private lateinit var factTextView: TextView

    override fun onFinishInflate() {
        super.onFinishInflate()
        factTextView = findViewById(R.id.fact_textView)
    }

    override fun populate(fact: Fact) {
        factTextView.text = fact.fact
    }
}

interface ICatsView {

    fun populate(fact: Fact)
}