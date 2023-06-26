package com.example.playlistmaker.playlist.mediateca.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentMediatecaBinding
import com.example.playlistmaker.playlist.mediateca.ui.adapter.PlatListViewPagerAdapter
import com.example.playlistmaker.playlist.util.BindingFragment
import com.google.android.material.tabs.TabLayoutMediator

class MediatecaFragment:BindingFragment<FragmentMediatecaBinding>() {
    private lateinit var tabMediator: TabLayoutMediator
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMediatecaBinding {
        return FragmentMediatecaBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = PlatListViewPagerAdapter(fragmentManager = childFragmentManager, lifecycle)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.Selected_tracks)
                1 -> tab.text = getString(R.string.Playlist)
            }
        }
        tabMediator.attach()
    }

}