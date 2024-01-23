package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }
    private lateinit var view: CatsView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.catsStateFlow.collectLatest {
                handlerResult(it)
            }
        }
    }

    fun handlerResult(result: Result<Fact>) {
        when(result){
            is Result.Error -> {
                Toast.makeText(this, result.exception, Toast.LENGTH_LONG).show()}
            is Result.Success<Any> -> {view.populate(result.data as Fact)}
        }
    }
}