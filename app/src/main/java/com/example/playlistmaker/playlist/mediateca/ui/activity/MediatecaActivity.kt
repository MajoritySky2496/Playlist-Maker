package com.example.playlistmaker.playlist.mediateca.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMediatecaBinding
import com.example.playlistmaker.playlist.mediateca.ui.adapter.PlatListViewPagerAdapter
import com.example.playlistmaker.playlist.util.NavigationRouter
import com.google.android.material.tabs.TabLayoutMediator

class MediatecaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMediatecaBinding
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediatecaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.leftArrow.setOnClickListener {
            NavigationRouter().goBack(this)
        }

        binding.viewPager.adapter = PlatListViewPagerAdapter(supportFragmentManager, lifecycle)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.Selected_tracks)
                1 -> tab.text = getString(R.string.Playlist)
            }
        }
        tabMediator.attach()


    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}