package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    var fontSize: Float
        get() = sharedPreferences.getFloat("font_size", 1.0f)
        set(value) = sharedPreferences.edit().putFloat("font_size", value).apply()

    var isDarkMode: Boolean
        get() = sharedPreferences.getBoolean("is_dark_mode", false)
        set(value) = sharedPreferences.edit().putBoolean("is_dark_mode", value).apply()

    fun applyTheme() {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}