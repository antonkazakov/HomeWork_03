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

    private val tvFact by lazy {
        findViewById<TextView>(R.id.fact_textView)
    }

    override fun populate(result: Result<Fact>) {
        when (result) {
            is Loading -> {
                doOnLoading()
            }
            is Success -> {
                doOnSuccess(result.data)
            }
            is Error -> {
                doOnError()
            }
        }

    }

    private fun doOnLoading() {
        tvFact.text = context.getString(R.string.loading_state_text)
    }

    private fun doOnSuccess(fact: Fact) {
        tvFact.text = fact.text
    }

    private fun doOnError() {
        val errorMessage = context.getString(R.string.error_state_text)
        val toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)

        toast.show()
    }
}

interface ICatsView {

    fun populate(result: Result<Fact>)
}