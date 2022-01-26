package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(fact: Fact) {
        findViewById<TextView>(R.id.fact_textView).text = fact.text
    }

    override fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun render(result: Result<Fact>) {
        when (result) {
            is Result.Success<Fact> -> {
                populate(result.value)
                findViewById<TextView>(R.id.fact_textView).visibility = View.VISIBLE
                findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
            }
            is Result.Error -> {
                showToast(result.message)
                findViewById<TextView>(R.id.fact_textView).visibility = View.GONE
                findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
            }
            is Result.Loading -> {
                findViewById<TextView>(R.id.fact_textView).visibility = View.GONE
                findViewById<ProgressBar>(R.id.progress).visibility = View.VISIBLE
            }
        }
    }
}

interface ICatsView {
    fun populate(fact: Fact)
    fun showToast(message: String?)
    fun render(result: Result<Fact>)
}