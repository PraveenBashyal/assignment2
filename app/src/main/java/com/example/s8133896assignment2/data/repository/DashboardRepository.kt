package com.example.s8133896assignment2.data.repository

import com.example.s8133896assignment2.data.model.DashboardResponse
import com.example.s8133896assignment2.data.remote.Nit3213Api
import retrofit2.Response

/**
 * Repository for loading Dashboard data from the NIT3213 API.
 */
class DashboardRepository(
    private val api: Nit3213Api
) {

    suspend fun getDashboard(
        keypass: String
    ): Response<DashboardResponse> {
        return api.getDashboard(keypass)
    }
}