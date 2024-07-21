package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.Toast

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(fact: Fact) {
        findViewById<TextView>(R.id.fact_textView).text = fact.text
    }

    override fun showError(messageError: String) {
        Toast.makeText( context, messageError, Toast.LENGTH_LONG).show()
    }
}

interface ICatsView {

    fun populate(fact: Fact)

    fun showError(messageError: String)
}