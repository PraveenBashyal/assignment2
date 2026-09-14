package com.example.s8133896assignment2.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s8133896assignment2.R
import com.example.s8133896assignment2.data.model.FitnessEntity

/**
 * Displays Fitness exercise summaries in the Dashboard RecyclerView.
 *
 * The description is intentionally not displayed here. It will be shown
 * only on the Details screen after the user selects an exercise.
 */
class FitnessAdapter(
    private val exercises: List<FitnessEntity>,
    private val onExerciseClicked: (FitnessEntity) -> Unit
) : RecyclerView.Adapter<FitnessAdapter.FitnessViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_fitness_exercise,
            parent,
            false
        )

        return FitnessViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FitnessViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount(): Int = exercises.size

    inner class FitnessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val exerciseNameText: TextView =
            itemView.findViewById(R.id.textViewExerciseName)

        private val muscleGroupText: TextView =
            itemView.findViewById(R.id.textViewMuscleGroup)

        private val equipmentText: TextView =
            itemView.findViewById(R.id.textViewEquipment)

        private val difficultyText: TextView =
            itemView.findViewById(R.id.textViewDifficulty)

        private val caloriesText: TextView =
            itemView.findViewById(R.id.textViewCalories)

        fun bind(exercise: FitnessEntity) {
            exerciseNameText.text = exercise.exerciseName
            muscleGroupText.text = "Muscle group: ${exercise.muscleGroup}"
            equipmentText.text = "Equipment: ${exercise.equipment}"
            difficultyText.text = "Difficulty: ${exercise.difficulty}"
            caloriesText.text =
                "Calories per hour: ${exercise.caloriesBurnedPerHour}"

            itemView.setOnClickListener {
                onExerciseClicked(exercise)
            }
        }
    }
}