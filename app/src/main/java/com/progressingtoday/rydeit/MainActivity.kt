package com.progressingtoday.rydeit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.progressingtoday.rydeit.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)

        // Inorder to user custom drawables
        navView.itemIconTintList = null

        // TODO: check user has logged in or not, if not only home item is enable.
        // updateNavState(isLogin = false)

        navView.setupWithNavController(navController)
    }

    private fun updateNavState(hasLoggedIn:Boolean) {
        val navView: BottomNavigationView = binding.navView
        navView.menu.findItem(R.id.navigation_purchase).isEnabled = false
        navView.menu.findItem(R.id.navigation_asset).isEnabled = false
        navView.menu.findItem(R.id.navigation_wallet).isEnabled = false
        navView.menu.findItem(R.id.navigation_account).isEnabled = false
    }



}