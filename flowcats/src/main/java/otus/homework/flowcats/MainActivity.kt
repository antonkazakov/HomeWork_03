package otus.homework.flowcats

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Базовая `activity`
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        findViewById<Button>(R.id.state_in_button).setOnClickListener {
            startActivity(
                Intent(this, otus.homework.flowcats.presentation.statein.CatsActivity::class.java)
            )
        }

        findViewById<Button>(R.id.base_button).setOnClickListener {
            startActivity(
                Intent(this, otus.homework.flowcats.presentation.base.CatsActivity::class.java)
            )
        }
    }
}