package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import otus.homework.flowcats.model.Result
import otus.homework.flowcats.model.Success
import java.lang.Exception

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(result: Result<*>) {
        when (result) {
            is Success<*> -> findViewById<TextView>(R.id.fact_textView).text = (result.get() as Fact).text
            else -> Toast.makeText(context.applicationContext, (result.get() as Exception).message, Toast.LENGTH_SHORT).show()
        }
    }
}

interface ICatsView {

    fun populate(result: Result<*>)
}