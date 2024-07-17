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

    override fun populate(result: Result) {
        when (result) {
            is Result.Success<*> -> {
                val fact = result.body as Fact
                findViewById<TextView>(R.id.fact_textView).text = fact.text
            }

            Result.Error -> Unit
            Result.Empty -> Unit
        }

    }
}

interface ICatsView {

    fun populate(result: Result)
}