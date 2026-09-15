package com.example.s8133896assignment2.ui.login

import com.example.s8133896assignment2.data.model.AuthResponse
import com.example.s8133896assignment2.data.repository.AuthRepositoryInterface
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

/**
 * Local unit tests for LoginViewModel input validation.
 *
 * These tests use a fake repository, so no API request is made.
 */
class LoginViewModelTest {

    @Test
    fun `blank student ID shows validation error`() {
        val viewModel = LoginViewModel(
            repository = FakeAuthRepository()
        )

        viewModel.login(
            studentId = "",
            firstName = "Alex"
        )

        val state = viewModel.uiState.value

        assertEquals(
            LoginUiState.Error(
                message = "Please enter your student ID."
            ),
            state
        )
    }

    @Test
    fun `blank first name shows validation error`() {
        val viewModel = LoginViewModel(
            repository = FakeAuthRepository()
        )

        viewModel.login(
            studentId = "8133896",
            firstName = ""
        )

        val state = viewModel.uiState.value

        assertEquals(
            LoginUiState.Error(
                message = "Please enter your first name."
            ),
            state
        )
    }

    /**
     * Test-only repository. It is never called in these validation tests.
     */
    private class FakeAuthRepository : AuthRepositoryInterface {

        override suspend fun login(
            studentId: String,
            firstName: String
        ): Response<AuthResponse> {
            return Response.success(
                AuthResponse(
                    keypass = "test-keypass"
                )
            )
        }
    }
}