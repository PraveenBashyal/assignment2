package com.example.s8133896assignment2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Details screen for one Fitness exercise.
 *
 * It receives all selected exercise values from DashboardActivity and shows
 * every property, including the description that is excluded from the list.
 */
class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val exerciseNameText =
            findViewById<TextView>(R.id.textViewDetailsExerciseName)

        val muscleGroupText =
            findViewById<TextView>(R.id.textViewDetailsMuscleGroup)

        val equipmentText =
            findViewById<TextView>(R.id.textViewDetailsEquipment)

        val difficultyText =
            findViewById<TextView>(R.id.textViewDetailsDifficulty)

        val caloriesText =
            findViewById<TextView>(R.id.textViewDetailsCalories)

        val descriptionText =
            findViewById<TextView>(R.id.textViewDetailsDescription)

        // Read the values passed by DashboardActivity.
        val exerciseName = intent.getStringExtra(EXTRA_EXERCISE_NAME).orEmpty()
        val muscleGroup = intent.getStringExtra(EXTRA_MUSCLE_GROUP).orEmpty()
        val equipment = intent.getStringExtra(EXTRA_EQUIPMENT).orEmpty()
        val difficulty = intent.getStringExtra(EXTRA_DIFFICULTY).orEmpty()
        val calories = intent.getDoubleExtra(EXTRA_CALORIES, 0.0)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION).orEmpty()

        // Display all entity information, including its description.
        exerciseNameText.text = exerciseName
        muscleGroupText.text = "Muscle group: $muscleGroup"
        equipmentText.text = "Equipment: $equipment"
        difficultyText.text = "Difficulty: $difficulty"
        caloriesText.text = "Calories per hour: $calories"
        descriptionText.text = description
    }

    companion object {
        // Intent keys used to transfer the selected item from DashboardActivity.
        const val EXTRA_EXERCISE_NAME = "extra_exercise_name"
        const val EXTRA_MUSCLE_GROUP = "extra_muscle_group"
        const val EXTRA_EQUIPMENT = "extra_equipment"
        const val EXTRA_DIFFICULTY = "extra_difficulty"
        const val EXTRA_CALORIES = "extra_calories"
        const val EXTRA_DESCRIPTION = "extra_description"
    }
}