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

    override fun populate(catsState: CatsState) {
        when (catsState) {
            CatsState.Default -> Unit
            is CatsState.CatsSuccess -> {
                findViewById<TextView>(R.id.fact_textView).text = catsState.fact.text
            }

            is CatsState.CatsError -> {
                findViewById<TextView>(R.id.fact_textView).text = catsState.error
            }
        }
    }
}

interface ICatsView {
    fun populate(catsState: CatsState)
}