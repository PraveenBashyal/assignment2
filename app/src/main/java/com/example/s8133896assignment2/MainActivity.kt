package com.example.s8133896assignment2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.s8133896assignment2.ui.login.LoginUiState
import com.example.s8133896assignment2.ui.login.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Login screen for the NIT3213 Assignment 2 application.
 *
 * The user enters their student ID and case-sensitive first name.
 * A successful Footscray API login returns a keypass, which is passed
 * to DashboardActivity to request the correct dashboard data.
 */
class MainActivity : AppCompatActivity() {

    // Koin injects the LoginViewModel and its AuthRepository dependency.
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keeps the app compatible with edge-to-edge Android displays.
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Connect XML views to Kotlin variables.
        val studentIdInput = findViewById<EditText>(R.id.editTextStudentId)
        val firstNameInput = findViewById<EditText>(R.id.editTextFirstName)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarLogin)

        // Validate input before calling the authentication API.
        loginButton.setOnClickListener {
            val studentId = studentIdInput.text.toString().trim()
            val firstName = firstNameInput.text.toString().trim()

            if (studentId.isEmpty()) {
                studentIdInput.error = "Enter your student ID"
                studentIdInput.requestFocus()
                return@setOnClickListener
            }

            if (firstName.isEmpty()) {
                firstNameInput.error = "Enter your first name"
                firstNameInput.requestFocus()
                return@setOnClickListener
            }

            // Calls POST /footscray/auth through the ViewModel and repository.
            loginViewModel.login(studentId, firstName)
        }

        // Observe LoginViewModel state only while this Activity is visible.
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.uiState.collect { state ->
                    when (state) {
                        LoginUiState.Idle -> {
                            progressBar.visibility = View.GONE
                            loginButton.isEnabled = true
                        }

                        LoginUiState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            loginButton.isEnabled = false
                        }

                        is LoginUiState.Success -> {
                            progressBar.visibility = View.GONE
                            loginButton.isEnabled = true

                            // Pass API keypass to DashboardActivity.
                            val dashboardIntent = Intent(
                                this@MainActivity,
                                DashboardActivity::class.java
                            )

                            dashboardIntent.putExtra(
                                DashboardActivity.EXTRA_KEYPASS,
                                state.keypass
                            )

                            startActivity(dashboardIntent)
                        }

                        is LoginUiState.Error -> {
                            progressBar.visibility = View.GONE
                            loginButton.isEnabled = true

                            Toast.makeText(
                                this@MainActivity,
                                state.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}