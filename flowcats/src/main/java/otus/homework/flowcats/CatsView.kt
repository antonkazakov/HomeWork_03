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

    override fun populate(result: Result) {
        when (result) {
            is Result.Success<*> -> {
                val fact = result.body as Fact
                findViewById<TextView>(R.id.fact_textView).text = fact.text
            }

            Result.Error -> {
                val errorMessage = context.getString(R.string.connection_error)
                showToast(errorMessage)
            }

            Result.Empty -> Unit
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}

interface ICatsView {

    fun populate(result: Result)
}