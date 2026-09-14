package com.example.s8133896assignment2.di

import com.example.s8133896assignment2.data.remote.Nit3213Api
import com.example.s8133896assignment2.data.repository.AuthRepository
import com.example.s8133896assignment2.data.repository.DashboardRepository
import com.example.s8133896assignment2.ui.dashboard.DashboardViewModel
import com.example.s8133896assignment2.ui.login.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Koin dependency-injection definitions for networking, repositories,
 * and ViewModels.
 */
val appModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://nit3213apinew.onrender.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(Nit3213Api::class.java)
    }

    single {
        AuthRepository(get())
    }

    single {
        DashboardRepository(get())
    }

    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        DashboardViewModel(get())
    }
}