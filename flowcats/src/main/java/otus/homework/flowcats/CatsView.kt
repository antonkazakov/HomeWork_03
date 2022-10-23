package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(fact: Result<Fact>) {
        when (fact) {
            is Result.Success -> {
                findViewById<TextView>(R.id.fact_textView).text = fact.entity.text
            }

            is Result.Error -> {
                Toast.makeText(context, fact.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

interface ICatsView {

    fun populate(fact: Result<Fact>)
}