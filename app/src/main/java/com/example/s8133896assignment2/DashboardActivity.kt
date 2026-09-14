package com.example.s8133896assignment2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s8133896assignment2.ui.dashboard.DashboardUiState
import com.example.s8133896assignment2.ui.dashboard.DashboardViewModel
import com.example.s8133896assignment2.ui.dashboard.FitnessAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Displays the Fitness Dashboard.
 *
 * The DashboardViewModel requests dashboard data through an injected
 * DashboardRepository. This Activity observes UI state and updates views.
 */
class DashboardActivity : AppCompatActivity() {

    // Koin provides the ViewModel with its injected DashboardRepository.
    private val dashboardViewModel: DashboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val entityTotalText = findViewById<TextView>(R.id.textViewEntityTotal)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarDashboard)
        val errorText = findViewById<TextView>(R.id.textViewDashboardError)
        val exercisesRecyclerView =
            findViewById<RecyclerView>(R.id.recyclerViewExercises)

        exercisesRecyclerView.layoutManager = LinearLayoutManager(this)

        // The keypass is passed from MainActivity after successful login.
        val keypass = intent.getStringExtra(EXTRA_KEYPASS)

        if (keypass.isNullOrBlank()) {
            progressBar.visibility = View.GONE
            errorText.visibility = View.VISIBLE
            errorText.text = "Dashboard error: missing keypass."
            return
        }

        // Render dashboard state only when this Activity is visible.
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dashboardViewModel.uiState.collect { state ->
                    when (state) {
                        DashboardUiState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            errorText.visibility = View.GONE
                        }

                        is DashboardUiState.Success -> {
                            progressBar.visibility = View.GONE
                            errorText.visibility = View.GONE

                            entityTotalText.text =
                                "Exercises available: ${state.dashboard.entityTotal}"

                            exercisesRecyclerView.adapter = FitnessAdapter(
                                exercises = state.dashboard.entities,
                                onExerciseClicked = { exercise ->
                                    openDetailsScreen(
                                        exerciseName = exercise.exerciseName,
                                        muscleGroup = exercise.muscleGroup,
                                        equipment = exercise.equipment,
                                        difficulty = exercise.difficulty,
                                        calories = exercise.caloriesBurnedPerHour,
                                        description = exercise.description
                                    )
                                }
                            )
                        }

                        is DashboardUiState.Error -> {
                            progressBar.visibility = View.GONE
                            errorText.visibility = View.VISIBLE
                            errorText.text = state.message
                        }
                    }
                }
            }
        }

        // Start the API request after observer setup.
        dashboardViewModel.loadDashboard(keypass)
    }

    /**
     * Opens the Details screen and transfers every field of the selected
     * Fitness entity, including its detailed description.
     */
    private fun openDetailsScreen(
        exerciseName: String,
        muscleGroup: String,
        equipment: String,
        difficulty: String,
        calories: Double,
        description: String
    ) {
        val detailsIntent = Intent(this, DetailsActivity::class.java)

        detailsIntent.putExtra(
            DetailsActivity.EXTRA_EXERCISE_NAME,
            exerciseName
        )

        detailsIntent.putExtra(
            DetailsActivity.EXTRA_MUSCLE_GROUP,
            muscleGroup
        )

        detailsIntent.putExtra(
            DetailsActivity.EXTRA_EQUIPMENT,
            equipment
        )

        detailsIntent.putExtra(
            DetailsActivity.EXTRA_DIFFICULTY,
            difficulty
        )

        detailsIntent.putExtra(
            DetailsActivity.EXTRA_CALORIES,
            calories
        )

        detailsIntent.putExtra(
            DetailsActivity.EXTRA_DESCRIPTION,
            description
        )

        startActivity(detailsIntent)
    }

    companion object {
        // Intent key for the API keypass received from MainActivity.
        const val EXTRA_KEYPASS = "extra_keypass"
    }
}