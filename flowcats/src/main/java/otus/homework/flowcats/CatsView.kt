package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import otus.homework.flowcats.Result

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(result: Result) {
        val textToShow: String = when (result){
            is Result.Success -> result.fact.text
            is Result.Error -> "Error: ${result.error}"
        }
        findViewById<TextView>(R.id.fact_textView).text = textToShow
    }
}

interface ICatsView {

    fun populate(result: Result)
}