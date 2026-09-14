package com.example.s8133896assignment2.data.remote

import com.example.s8133896assignment2.data.model.AuthRequest
import com.example.s8133896assignment2.data.model.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Nit3213Api {

    @POST("footscray/auth")
    suspend fun login(
        @Body request: AuthRequest
    ): Response<AuthResponse>

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(
        @Path("keypass") keypass: String
    ): Response<Map<String, Any>>
}