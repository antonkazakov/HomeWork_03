package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.CircularProgressIndicator

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    private val progressIndicator by lazy { findViewById<CircularProgressIndicator>(R.id.progress_indicator) }

    override fun populate(fact: Fact) {
        progressIndicator.isVisible = false
        findViewById<TextView>(R.id.fact_textView).text = fact.text
    }

    override fun showError(message: String) {
        progressIndicator.isVisible = false
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressIndicator.isVisible = true
    }
}

interface ICatsView {

    fun populate(fact: Fact)
    fun showError(message : String)
    fun showLoading()
}