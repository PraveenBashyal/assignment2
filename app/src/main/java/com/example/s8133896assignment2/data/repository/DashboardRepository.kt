package com.example.s8133896assignment2.data.repository

import com.example.s8133896assignment2.data.remote.Nit3213Api
import retrofit2.Response

class DashboardRepository(
    private val api: Nit3213Api
) {

    suspend fun getDashboard(
        keypass: String
    ): Response<Map<String, Any>> {
        return api.getDashboard(keypass)
    }
}