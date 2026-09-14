package com.example.s8133896assignment2.data.repository

import com.example.s8133896assignment2.data.model.AuthResponse
import retrofit2.Response

/**
 * Contract for authentication data operations.
 *
 * The ViewModel depends on this interface, which lets unit tests use a
 * fake repository without calling the real NIT3213 API.
 */
interface AuthRepositoryInterface {

    suspend fun login(
        studentId: String,
        firstName: String
    ): Response<AuthResponse>
}