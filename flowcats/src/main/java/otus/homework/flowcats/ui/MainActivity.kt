package otus.homework.flowcats.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import otus.homework.flowcats.*
import otus.homework.flowcats.di.DiContainer
import otus.homework.flowcats.ui.model.Result

class MainActivity : AppCompatActivity() {

	private val diContainer = DiContainer()
	private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
		setContentView(view)

	 	lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				catsViewModel.catsSharedFlow.collect { result ->
					when (result) {
						is Result.Error -> {
							view.setProgressVisibility(false)
							view.showError(result.msg)
						}

						is Result.Success -> {
							view.setProgressVisibility(false)
							view.populate(result.data)
						}

						is Result.InProgress -> {
							view.setProgressVisibility(true)
						}
					}
				}
			}
		}
	}
}