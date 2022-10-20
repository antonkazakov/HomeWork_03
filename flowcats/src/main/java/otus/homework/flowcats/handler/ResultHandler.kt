package otus.homework.flowcats.handler

import android.content.Context
import android.widget.Toast
import otus.homework.flowcats.Fact

class ResultHandler(private val context: Context) : ResultEvent {

    override fun onResult(result: Result<*>) {
        when (result) {
            is Result.Success -> onSuccess(result.data as Fact)
            is Result.Error -> onError(result.error)
        }
    }

    private fun onSuccess(fact: Fact) {
        if (fact.fact.isNotEmpty()) showToast(fact.fact)
    }

    private fun onError(throwable: Throwable) {
        showToast(throwable.message ?: "error")
    }


    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}