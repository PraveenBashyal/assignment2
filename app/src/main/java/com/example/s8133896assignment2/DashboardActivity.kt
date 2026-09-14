package com.example.s8133896assignment2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
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
 * It loads Fitness entities with the keypass returned after Login, displays
 * summary data in a RecyclerView, and opens DetailsActivity when an item is tapped.
 */
class DashboardActivity : AppCompatActivity() {

    // Koin injects the API repository.
    private val dashboardRepository: DashboardRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val entityTotalText = findViewById<TextView>(R.id.textViewEntityTotal)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarDashboard)
        val errorText = findViewById<TextView>(R.id.textViewDashboardError)
        val exercisesRecyclerView =
            findViewById<RecyclerView>(R.id.recyclerViewExercises)

        exercisesRecyclerView.layoutManager = LinearLayoutManager(this)

        // The keypass identifies the user's assigned Dashboard topic.
        val keypass = intent.getStringExtra(EXTRA_KEYPASS)

        if (keypass.isNullOrBlank()) {
            progressBar.visibility = View.GONE
            errorText.visibility = View.VISIBLE
            errorText.text = "Dashboard error: missing keypass."
            return
        }

        // Fetch dashboard entities without blocking the UI.
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
                            // Pass the complete selected entity to DetailsActivity.
                            val detailsIntent = Intent(
                                this@DashboardActivity,
                                DetailsActivity::class.java
                            )

                            detailsIntent.putExtra(
                                DetailsActivity.EXTRA_EXERCISE_NAME,
                                exercise.exerciseName
                            )

                            detailsIntent.putExtra(
                                DetailsActivity.EXTRA_MUSCLE_GROUP,
                                exercise.muscleGroup
                            )

                            detailsIntent.putExtra(
                                DetailsActivity.EXTRA_EQUIPMENT,
                                exercise.equipment
                            )

                            detailsIntent.putExtra(
                                DetailsActivity.EXTRA_DIFFICULTY,
                                exercise.difficulty
                            )

                            detailsIntent.putExtra(
                                DetailsActivity.EXTRA_CALORIES,
                                exercise.caloriesBurnedPerHour
                            )

                            detailsIntent.putExtra(
                                DetailsActivity.EXTRA_DESCRIPTION,
                                exercise.description
                            )

                            startActivity(detailsIntent)
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
        // Intent key used to receive the login keypass from MainActivity.
        const val EXTRA_KEYPASS = "extra_keypass"
    }
}