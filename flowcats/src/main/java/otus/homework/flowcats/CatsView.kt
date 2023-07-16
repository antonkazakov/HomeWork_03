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

    lateinit var textView: TextView

    override fun onFinishInflate() {
        super.onFinishInflate()
        textView = findViewById<TextView>(R.id.fact_textView)
    }

    override fun populate(result: Result) {
        when (result) {
            is Result.Success -> textView.text = result.fact.text
            is Result.Error -> textView.text = result.msg
            is Result.Loading -> textView.text = context.getString(R.string.loading)
        }
    }
}

interface ICatsView {

    fun populate(result: Result)
}