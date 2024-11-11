package com.example.eventdicoding.ui.setting

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.eventdicoding.R
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(requireContext())
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = view.findViewById<SwitchMaterial>(R.id.switch_theme)

        lifecycleScope.launch {
            val isDarkMode = dataStoreManager.themeFlow.first()
            switchTheme.isChecked = isDarkMode
            updateTheme(isDarkMode)
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            lifecycleScope.launch {
                dataStoreManager.setTheme(isChecked)
            }
            updateTheme(isChecked)
        }
    }

    private fun updateTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}