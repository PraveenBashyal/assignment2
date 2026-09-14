package com.example.s8133896assignment2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.s8133896assignment2.data.repository.DashboardRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Temporary dashboard screen.
 *
 * It requests the dashboard data using the keypass returned from the
 * Footscray authentication endpoint and displays the raw response.
 *
 * This lets us identify the exact Fitness entity fields before creating
 * the final RecyclerView dashboard and Details screen.
 */
class DashboardActivity : AppCompatActivity() {

    // Koin provides the repository configured with Retrofit and the API service.
    private val dashboardRepository: DashboardRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Temporary screen content for inspecting the API response.
        val dashboardText = TextView(this).apply {
            textSize = 16f
            setPadding(32, 32, 32, 32)
            text = "Loading Fitness dashboard..."
        }

        setContentView(dashboardText)

        // Keypass is sent from MainActivity after successful authentication.
        val keypass = intent.getStringExtra(EXTRA_KEYPASS)

        // Do not make an API request if this Activity was opened without a keypass.
        if (keypass.isNullOrBlank()) {
            dashboardText.text = "Dashboard error: missing keypass."
            return
        }

        // Run the network request without freezing the user interface.
        lifecycleScope.launch {
            try {
                val response = dashboardRepository.getDashboard(keypass)

                if (response.isSuccessful && response.body() != null) {
                    dashboardText.text = response.body().toString()
                } else {
                    dashboardText.text =
                        "Dashboard request failed. HTTP code: ${response.code()}"
                }
            } catch (exception: Exception) {
                dashboardText.text =
                    "Network error while loading dashboard: ${exception.message}"
            }
        }
    }

    companion object {
        // Intent key used to pass the API keypass from MainActivity.
        const val EXTRA_KEYPASS = "extra_keypass"
    }
}