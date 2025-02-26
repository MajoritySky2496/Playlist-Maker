package com.soundhaven.app.playlist.main.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soundhaven.app.R
import com.soundhaven.app.playlist.di.dagger.ViewModelFactory
import com.soundhaven.app.playlist.main.app.App
import javax.inject.Inject

class RootActivity : AppCompatActivity() {
   lateinit var  bottomNavigationView:BottomNavigationView
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {



        (applicationContext as App).appComponent.injectRootActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mediatecaFragment -> bottomNavigationView.visibility = View.VISIBLE
                R.id.searchFragment -> bottomNavigationView.visibility = View.VISIBLE
                R.id.playListFragment -> bottomNavigationView.visibility = View.GONE
                R.id.aboutPlayListFragment -> bottomNavigationView.visibility = View.GONE
            }
        }



    }

}