package com.example.s8133896assignment2

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s8133896assignment2.data.repository.DashboardRepository
import com.example.s8133896assignment2.ui.dashboard.FitnessAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Dashboard screen for Fitness exercises.
 *
 * It uses the keypass returned by LoginViewModel to request the appropriate
 * dashboard data, then displays the exercise summaries in a RecyclerView.
 * Descriptions are excluded here and will appear on the Details screen.
 */
class DashboardActivity : AppCompatActivity() {

    // Koin injects the repository containing the Retrofit dashboard request.
    private val dashboardRepository: DashboardRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val entityTotalText = findViewById<TextView>(R.id.textViewEntityTotal)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarDashboard)
        val errorText = findViewById<TextView>(R.id.textViewDashboardError)
        val exercisesRecyclerView =
            findViewById<RecyclerView>(R.id.recyclerViewExercises)

        // RecyclerView needs a layout manager before it can display items.
        exercisesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Read the keypass sent from MainActivity after a successful login.
        val keypass = intent.getStringExtra(EXTRA_KEYPASS)

        if (keypass.isNullOrBlank()) {
            progressBar.visibility = View.GONE
            errorText.visibility = View.VISIBLE
            errorText.text = "Dashboard error: missing keypass."
            return
        }

        // Load Dashboard data without blocking the user interface.
        lifecycleScope.launch {
            try {
                val response = dashboardRepository.getDashboard(keypass)

                if (response.isSuccessful && response.body() != null) {
                    val dashboard = response.body()!!

                    progressBar.visibility = View.GONE
                    entityTotalText.text =
                        "Exercises available: ${dashboard.entityTotal}"

                    exercisesRecyclerView.adapter = FitnessAdapter(
                        exercises = dashboard.entities,
                        onExerciseClicked = { exercise ->
                            // Temporary tap test. DetailsActivity is added next.
                            Toast.makeText(
                                this@DashboardActivity,
                                "Selected: ${exercise.exerciseName}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                } else {
                    progressBar.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                    errorText.text =
                        "Unable to load dashboard. HTTP code: ${response.code()}"
                }
            } catch (exception: Exception) {
                progressBar.visibility = View.GONE
                errorText.visibility = View.VISIBLE
                errorText.text =
                    "Network error while loading dashboard. Please try again."
            }
        }
    }

    companion object {
        // Intent key used to receive the API keypass from MainActivity.
        const val EXTRA_KEYPASS = "extra_keypass"
    }
}