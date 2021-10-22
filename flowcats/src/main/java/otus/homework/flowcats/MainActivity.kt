package otus.homework.flowcats

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        when (val result = catsViewModel.catsStateFlow.value) {
            is ResultModel.Success -> view.populate(result.answer)
            is ResultModel.Error -> Toast.makeText(
                this,
                result.exception.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}