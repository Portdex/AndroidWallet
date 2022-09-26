package com.application.portdex

import android.app.Application
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.AmplifyConfiguration
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.jacopo.pagury.prefs.PrefUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PrefUtils.initialize(this)

        try {
            val config = AmplifyConfiguration.builder(applicationContext)
                .devMenuEnabled(true)
                .build()
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(config, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}