package com.example.s8133896assignment2.data.repository

import com.example.s8133896assignment2.data.model.AuthRequest
import com.example.s8133896assignment2.data.model.AuthResponse
import com.example.s8133896assignment2.data.remote.Nit3213Api
import retrofit2.Response

class AuthRepository(
    private val api: Nit3213Api
) {

    suspend fun login(
        studentId: String,
        firstName: String
    ): Response<AuthResponse> {
        return api.login(
            AuthRequest(
                username = studentId,
                password = firstName
            )
        )
    }
}