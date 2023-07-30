package otus.homework.flowcats.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import otus.homework.flowcats.R
import otus.homework.flowcats.domain.Cat

/**
 * `Custom view` с информацией о коте.
 */
class CatsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    private lateinit var textView: TextView

    override fun onFinishInflate() {
        super.onFinishInflate()
        textView = findViewById(R.id.fact_textView)
    }

    override fun populate(cat: Cat) {
        textView.text = cat.fact
    }

    override fun warn(message: String) {
        textView.text = message
    }

    override fun reset() {
        textView.text = ""
    }
}

/**
 * Интерфейс взаимодействия с `View`
 */
interface ICatsView {

    /** Обновить данные о коте [Cat] */
    fun populate(cat: Cat)

    /** Оповестить о возникшей ошибке с причиной [message] */
    fun warn(message: String)

    /** Очистить данные о коте */
    fun reset()
}