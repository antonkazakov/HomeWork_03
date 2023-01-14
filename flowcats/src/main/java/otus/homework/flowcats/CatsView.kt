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
        findViewById<TextView>(R.id.fact_textView).text = fact.fact
    }

    override fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideLoader() {
        findViewById<ProgressBar>(R.id.fact_progressBar).visibility = View.GONE
    }

    override fun showLoader() {
        findViewById<ProgressBar>(R.id.fact_progressBar).visibility = View.VISIBLE
    }
}

interface ICatsView {

    fun populate(fact: Fact)

    fun showToast(message: String?)

    fun hideLoader()

    fun showLoader()

}