package com.example.s8133896assignment2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val studentIdInput = findViewById<EditText>(R.id.editTextStudentId)
        val firstNameInput = findViewById<EditText>(R.id.editTextFirstName)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

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

            Toast.makeText(
                this,
                "Login details accepted for $firstName",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}