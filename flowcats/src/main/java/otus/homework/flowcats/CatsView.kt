package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    private lateinit var textForFact: TextView
    private lateinit var progress: ProgressBar

    override fun onFinishInflate() {
        super.onFinishInflate()
        textForFact = findViewById(R.id.fact_textView)
        progress = findViewById(R.id.progress)
    }

    override fun populate(fact: Fact) {
        textForFact.text = fact.text
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(isLoading: Boolean) {
        if(isLoading) {
            progress.visibility = VISIBLE
            textForFact.visibility = INVISIBLE
        } else {
            progress.visibility = INVISIBLE
            textForFact.visibility = VISIBLE
        }
    }
}

interface ICatsView {
    fun populate(fact: Fact)
    fun showToast(message: String)
    fun showProgress(isLoading: Boolean)
}
