package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import otus.homework.flowcats.model.Fact
import otus.homework.flowcats.model.Result

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(state: Result) {
        when (state) {
            is Result.Success<*> -> {
                if (state.data is Fact) {
                    findViewById<TextView>(R.id.fact_textView).text = state.data.text
                }
            }

            is Result.Error -> {
                Toast.makeText(context, state.error.message ?: "...", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

interface ICatsView {

    fun populate(state: Result)
}