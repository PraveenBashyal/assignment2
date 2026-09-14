package com.example.s8133896assignment2

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

class MainActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val studentIdInput = findViewById<EditText>(R.id.editTextStudentId)
        val firstNameInput = findViewById<EditText>(R.id.editTextFirstName)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarLogin)

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

            loginViewModel.login(studentId, firstName)
        }

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

                            Toast.makeText(
                                this@MainActivity,
                                "Keypass: ${state.keypass}",
                                Toast.LENGTH_LONG
                            ).show()
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