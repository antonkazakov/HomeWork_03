package otus.homework.flowcats.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import otus.homework.flowcats.R
import otus.homework.flowcats.ui.model.Fact

class CatsView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

	override fun populate(fact: Fact) {
		findViewById<TextView>(R.id.fact_textView).text = fact.text
	}

    override fun showError(msg: String) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

	override fun setProgressVisibility(isVisible: Boolean) {
		findViewById<View>(R.id.progressBar)?.isVisible = isVisible
	}
}

interface ICatsView {

	fun populate(fact: Fact)
	fun showError(msg: String)
	fun setProgressVisibility(isVisible:Boolean)
}