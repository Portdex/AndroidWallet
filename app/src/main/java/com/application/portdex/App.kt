package com.application.portdex

import android.app.Application
import android.content.Context
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.AmplifyConfiguration
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var instance: App

        fun getContext(): Context {
            return instance.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        PrefUtils.initialize(this)

        try {
            val config = AmplifyConfiguration.builder(applicationContext)
                .devMenuEnabled(false)
                .build()
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(config, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}