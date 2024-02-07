package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.dataState.collectLatest { result ->
                // ошибки и успех
                when (result) {
                    // сообщить об ошибке
                    is Result.Error -> {
                        if (result.exception is SocketTimeoutException) {
                            Toast.makeText(
                                applicationContext,
                                "Не удалось получить ответ от сервера",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                result.exception.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    // отрисовать данные
                    is Result.Success -> view.populate(fact = result.data)
                    is Result.Loading -> Unit
                }
            }
        }
    }
}