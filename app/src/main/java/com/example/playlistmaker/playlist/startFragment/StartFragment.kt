package com.example.playlistmaker.playlist.startFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentStartBinding
import com.example.playlistmaker.playlist.util.BindingFragment

class StartFragment:BindingFragment<FragmentStartBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartBinding {
        return FragmentStartBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_searchFragment)
        }
        binding.mediatecaButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_mediatecaFragment)
        }
        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_settingFragment)
        }
    }
}