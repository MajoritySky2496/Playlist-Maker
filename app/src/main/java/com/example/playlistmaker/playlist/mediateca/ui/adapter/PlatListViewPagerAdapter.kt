package com.example.playlistmaker.playlist.mediateca.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.playlist.mediateca.ui.fragments.PlayListFragment
import com.example.playlistmaker.playlist.mediateca.ui.fragments.SelectedTracksFragment

class PlatListViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return PlayListFragment.newInstance()
            else -> return SelectedTracksFragment.newInstance()
        }
    }
}