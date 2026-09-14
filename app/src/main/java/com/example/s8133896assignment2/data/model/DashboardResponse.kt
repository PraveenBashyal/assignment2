package com.example.s8133896assignment2.data.model

/**
 * Full JSON response returned by GET /dashboard/fitness.
 */
data class DashboardResponse(
    val entities: List<FitnessEntity>,
    val entityTotal: Int
)