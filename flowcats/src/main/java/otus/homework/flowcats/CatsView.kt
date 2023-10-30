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

    override fun populate(fact: Fact?) {
        findViewById<TextView>(R.id.fact_textView).text = fact?.fact
    }
    override fun makeToast(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}

interface ICatsView {
    fun makeToast(error: String)
    fun populate(fact: Fact?)
}