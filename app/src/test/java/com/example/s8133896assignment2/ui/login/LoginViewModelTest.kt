package com.example.s8133896assignment2.ui.login

import com.example.s8133896assignment2.data.model.AuthResponse
import com.example.s8133896assignment2.data.repository.AuthRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import retrofit2.Response

/**
 * Unit tests for LoginViewModel.
 *
 * A fake repository avoids making real API requests while testing
 * the ViewModel's UI state behavior.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with blank student ID shows validation error`() = runTest {
        val viewModel = LoginViewModel(
            repository = FakeAuthRepository()
        )

        viewModel.login(
            studentId = "",
            firstName = "Alex"
        )

        val state = viewModel.uiState.first {
            it is LoginUiState.Error
        }

        assertEquals(
            "Please enter your student ID.",
            (state as LoginUiState.Error).message
        )
    }

    @Test
    fun `login with blank first name shows validation error`() = runTest {
        val viewModel = LoginViewModel(
            repository = FakeAuthRepository()
        )

        viewModel.login(
            studentId = "s8133896",
            firstName = ""
        )

        val state = viewModel.uiState.first {
            it is LoginUiState.Error
        }

        assertEquals(
            "Please enter your first name.",
            (state as LoginUiState.Error).message
        )
    }

    /**
     * Minimal fake implementation used only by these tests.
     */
    private class FakeAuthRepository : AuthRepositoryInterface {

        override suspend fun login(
            studentId: String,
            firstName: String
        ): Response<AuthResponse> {
            return Response.success(
                AuthResponse(
                    keypass = "fake-keypass"
                )
            )
        }
    }
}