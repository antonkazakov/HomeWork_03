package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(result: Result<Fact>) {
        when (result) {
            is Result.Error -> Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
            is Result.Loading -> Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
            is Result.Success -> findViewById<TextView>(R.id.fact_textView).text = result.data.text
        }
    }
}

interface ICatsView {

    fun populate(result: Result<Fact>)
}