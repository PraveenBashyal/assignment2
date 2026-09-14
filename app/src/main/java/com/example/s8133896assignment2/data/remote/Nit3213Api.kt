package com.example.s8133896assignment2.data.remote

import com.example.s8133896assignment2.data.model.AuthRequest
import com.example.s8133896assignment2.data.model.AuthResponse
import com.example.s8133896assignment2.data.model.DashboardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit definition of the NIT3213 API endpoints used by this app.
 */
interface Nit3213Api {

    /**
     * Authenticates a Footscray student.
     * A successful response contains the dashboard keypass.
     */
    @POST("footscray/auth")
    suspend fun login(
        @Body request: AuthRequest
    ): Response<AuthResponse>

    /**
     * Retrieves entities for the assigned dashboard topic.
     * For this project the keypass is "fitness".
     */
    @GET("dashboard/{keypass}")
    suspend fun getDashboard(
        @Path("keypass") keypass: String
    ): Response<DashboardResponse>
}