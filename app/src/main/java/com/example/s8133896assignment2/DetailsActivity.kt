package com.example.s8133896assignment2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Displays all available information for the Fitness exercise selected
 * from the Dashboard RecyclerView.
 */
class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Closes this screen and returns to the existing DashboardActivity.
        findViewById<TextView>(R.id.buttonBackToDashboard).setOnClickListener {
            finish()
        }

        val exerciseName = intent.getStringExtra(EXTRA_EXERCISE_NAME).orEmpty()
        val muscleGroup = intent.getStringExtra(EXTRA_MUSCLE_GROUP).orEmpty()
        val equipment = intent.getStringExtra(EXTRA_EQUIPMENT).orEmpty()
        val difficulty = intent.getStringExtra(EXTRA_DIFFICULTY).orEmpty()
        val calories = intent.getDoubleExtra(EXTRA_CALORIES, 0.0)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION).orEmpty()

        findViewById<TextView>(R.id.textViewDetailsExerciseName).text = exerciseName
        findViewById<TextView>(R.id.textViewDetailsMuscleGroup).text =
            "Muscle group: $muscleGroup"

        findViewById<TextView>(R.id.textViewDetailsEquipment).text =
            "Equipment: $equipment"

        findViewById<TextView>(R.id.textViewDetailsDifficulty).text =
            "Difficulty: $difficulty"

        findViewById<TextView>(R.id.textViewDetailsCalories).text =
            "Calories per hour: $calories"

        findViewById<TextView>(R.id.textViewDetailsDescription).text = description
    }

    companion object {
        const val EXTRA_EXERCISE_NAME = "extra_exercise_name"
        const val EXTRA_MUSCLE_GROUP = "extra_muscle_group"
        const val EXTRA_EQUIPMENT = "extra_equipment"
        const val EXTRA_DIFFICULTY = "extra_difficulty"
        const val EXTRA_CALORIES = "extra_calories"
        const val EXTRA_DESCRIPTION = "extra_description"
    }
}