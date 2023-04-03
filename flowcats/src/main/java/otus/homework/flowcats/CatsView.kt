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
        val textView = findViewById<TextView>(R.id.fact_textView)
        when (result) {
            is Result.Error -> textView.text = result.message
            is Result.Success<Fact> -> textView.text = result.response.text
        }
    }
}

interface ICatsView {

    fun populate(result: Result<Fact>)
}