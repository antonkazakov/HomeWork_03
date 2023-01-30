package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import otus.homework.flow.SampleInteractor
import otus.homework.flow.SampleRepository

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        /*catsViewModel.catsLiveData.observe(this){
            view.populate(it)
        }*/
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                catsViewModel.catsStateFlow.collect {
                    when(it){
                        Error -> view.showToast(getString(R.string.error))
                        is Success -> view.populate(it.item)
                    }
                }
            }
        }

        // Для тестов
        val interactor = SampleInteractor(object : SampleRepository{
            override fun produceNumbers(): Flow<Int>  = flowOf(1, 2, 4, 21, 30, 51, 3, 15, 45, 11, 60, 91, 5, 6, 7)

            override fun produceColors(): Flow<String> = flowOf("red", "orange", "yellow", "green", "light blue", "blue", "purple")

            override fun produceForms(): Flow<String> = flowOf("square", "rectangle", "circle", "triangle")

            override fun completed() {
                Log.d("TASK", "COMPLETED")
            }

        })

        interactor.main()

    }
}