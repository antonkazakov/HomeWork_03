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

    override fun populate(text: String) {
        findViewById<TextView>(R.id.fact_textView).text = text
    }

    override fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}

interface ICatsView {
    fun populate(text: String)
    fun toast(text: String)
}