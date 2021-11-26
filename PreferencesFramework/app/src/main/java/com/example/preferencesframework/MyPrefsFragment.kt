package com.example.preferencesframework

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.preference.ListPreference
import android.preference.PreferenceFragment

class MyPrefsFragment : PreferenceFragment(),
    OnSharedPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
        updateListPrefSummary()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun updateListPrefSummary() {
        val preference = findPreference("pref_style") as ListPreference
        val entry = preference.entry
        preference.summary = "Текущая настройка: $entry"
    }

    override fun onSharedPreferenceChanged(
        sharedPreferences: SharedPreferences,
        key: String
    ) {
        if (key == "pref_style") {
            updateListPrefSummary()
        }
    }
}