package otus.homework.flowcats.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import otus.homework.flowcats.R
import otus.homework.flowcats.domain.CatModel

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(fact: CatModel) {
        findViewById<TextView>(R.id.fact_textView).text = fact.factText
    }
}

interface ICatsView {

    fun populate(fact: CatModel)
}