package com.example.s8133896assignment2.data.model

/**
 * One exercise returned by the Fitness dashboard endpoint.
 *
 * Description is intentionally included here so the Details screen
 * can display all information for the selected entity.
 */
data class FitnessEntity(
    val exerciseName: String,
    val muscleGroup: String,
    val equipment: String,
    val difficulty: String,
    val caloriesBurnedPerHour: Double,
    val description: String
)