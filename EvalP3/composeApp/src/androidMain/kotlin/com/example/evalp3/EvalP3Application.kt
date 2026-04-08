package com.example.evalp3

import android.app.Application
import com.example.evalp3.common.sharedModules
import com.example.evalp3.data.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EvalP3Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EvalP3Application)
            modules(sharedModules() + platformModule)
        }
    }
}
