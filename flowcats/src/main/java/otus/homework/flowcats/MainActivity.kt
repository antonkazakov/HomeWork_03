package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import otus.homework.flowcats.data.CatsError
import otus.homework.flowcats.data.Success

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                catsViewModel.catsLiveData.onEach {
                    if (it is CatsError) showToast(it.throwable?.message.toString()) else view.populate(it.fact!!)
                }.collect() // Обработка Success и Error работает, но кажется мне кривоватой.
                //Если есть хорошая практика - напишите мне пожалуйста
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}