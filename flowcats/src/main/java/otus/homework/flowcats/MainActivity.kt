package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

  private val diContainer = DiContainer()
  private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
    setContentView(view)

    lifecycleScope.launch {
      catsViewModel.catsStateFlow.collect {
        when (it) {
          is Success<*> ->
            if (it.data is Fact)
              view.populate(it.data)
            else view.populate(
              Fact("Ooops, that's not a cat fact at all!")
            )
          is Error -> view.show(it.message)
          is Empty -> view.populate(Fact("Loading..."))
        }
      }
    }
  }
}
