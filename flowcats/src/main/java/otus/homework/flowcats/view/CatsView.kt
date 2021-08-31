package otus.homework.flowcats.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import otus.homework.flowcats.R
import otus.homework.flowcats.model.Fact

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun setFactToView(fact: Fact) {
        Log.i("!!!", "setFactToView: ${fact.text}")
        findViewById<TextView>(R.id.fact_textView).text = fact.text
    }
}

interface ICatsView {
    fun setFactToView(fact: Fact)
}