package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

typealias ButtonCallback = () -> Unit

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(fact: Fact) {
        findViewById<TextView>(R.id.fact_textView).text = fact.fact
    }

    fun setListeningCallbacks(
        startCallback: ButtonCallback,
        stopCallback: ButtonCallback
    ) {
        findViewById<Button>(R.id.startListening).setOnClickListener {
            startCallback.invoke()
        }

        findViewById<Button>(R.id.stopListening).setOnClickListener {
            stopCallback.invoke()
        }
    }
}

interface ICatsView {

    fun populate(fact: Fact)
}