package com.soundhaven.app.playlist.settings.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.soundhaven.app.databinding.FragmentSettingsBinding
import com.soundhaven.app.playlist.settings.presentation.SettingsViewModel
import com.soundhaven.app.playlist.util.BindingFragment
import com.google.android.material.switchmaterial.SwitchMaterial
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment:BindingFragment<FragmentSettingsBinding>() {
    lateinit var themeSwitcher: SwitchMaterial
    lateinit var shareButton: TextView
    lateinit var writeToSupportButton: TextView
    lateinit var userAgreementbutton: TextView
    val viewModel by viewModel<SettingsViewModel> ()
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        if (viewModel.getThemeSettings() == true) { themeSwitcher.toggle() }

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            viewModel.switchTheme(checked)

        }
        shareButton.setOnClickListener {
            viewModel.shareApp()
        }
        writeToSupportButton.setOnClickListener {
            viewModel.openSupport()
        }
        userAgreementbutton.setOnClickListener {
            viewModel.openTerms()
        }
    }
    fun initView() {
        shareButton = binding.shareButton
        writeToSupportButton = binding.writeToSupportButton
        userAgreementbutton = binding.userAgreementButton
        themeSwitcher = binding.themeSwitcher
    }
}