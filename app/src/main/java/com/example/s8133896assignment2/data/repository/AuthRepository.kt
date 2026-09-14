package com.example.s8133896assignment2.data.repository

import com.example.s8133896assignment2.data.model.AuthRequest
import com.example.s8133896assignment2.data.model.AuthResponse
import com.example.s8133896assignment2.data.remote.Nit3213Api
import retrofit2.Response

/**
 * Production authentication repository.
 *
 * It sends the entered student ID and first name to the Footscray API.
 */
class AuthRepository(
    private val api: Nit3213Api
) : AuthRepositoryInterface {

    override suspend fun login(
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