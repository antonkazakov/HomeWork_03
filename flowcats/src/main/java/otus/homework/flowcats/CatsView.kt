package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(catImageState: Result<String>) {
        when (catImageState) {
            is Result.Success -> displayImage(catImageState.value)
            is Result.Error -> displayError(catImageState.errorMessage)
        }
    }

    private fun displayImage(url: String) {
        findViewById<TextView>(R.id.error_message).visibility = View.GONE
        url.takeIf { it.isNotEmpty() }?.let {
            Picasso.get()
                .load(url)
                .into(findViewById<ImageView>(R.id.cat_image))
        }
    }

    private fun displayError(errorMessage: String?) {
        findViewById<TextView>(R.id.error_message).apply {
            text = errorMessage
            visibility = View.VISIBLE
        }
    }
}

interface ICatsView {

    fun populate(catImageState: Result<String>)
}