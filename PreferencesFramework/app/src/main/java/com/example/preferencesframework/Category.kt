package com.example.preferencesframework

import android.os.Bundle
import android.preference.PreferenceActivity

class Category : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.category)
    }
}