package com.example.s8133896assignment2

import android.app.Application
import com.example.s8133896assignment2.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Nit3213Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Nit3213Application)
            modules(appModule)
        }
    }
}