package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val prefs = PreferencesManager(newBase)
        val config = Configuration(newBase.resources.configuration)
        config.fontScale = prefs.fontSize
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }
}