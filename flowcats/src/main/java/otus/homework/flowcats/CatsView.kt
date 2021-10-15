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

    override fun populate(result: Result<Fact>) {
        findViewById<TextView>(R.id.fact_textView).text = when (result) {
            is Result.Placeholder -> result.message
            is Result.Success -> result.data.text
            is Result.Error -> result.message
        }
    }
}

interface ICatsView {

    fun populate(result: Result<Fact>)
}