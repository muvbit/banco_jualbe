package es.muvment.banco_jualbe.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import es.muvment.banco_jualbe.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}