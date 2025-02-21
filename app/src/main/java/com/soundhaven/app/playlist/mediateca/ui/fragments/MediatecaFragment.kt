package com.soundhaven.app.playlist.mediateca.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soundhaven.app.R
import com.soundhaven.app.databinding.FragmentMediatecaBinding
import com.soundhaven.app.playlist.mediateca.ui.adapter.PlatListViewPagerAdapter
import com.soundhaven.app.playlist.util.BindingFragment
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