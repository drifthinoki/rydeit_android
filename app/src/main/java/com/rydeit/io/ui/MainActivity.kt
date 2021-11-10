package com.rydeit.io.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rydeit.io.R
import com.rydeit.io.databinding.ActivityMainBinding
import com.rydeit.io.utils.StorageUtil


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        initListener()

        // init StorageUtil
        StorageUtil.with(application)
    }

    private fun updateNavState(hasLoggedIn:Boolean) {
        val navView: BottomNavigationView = binding.navView
        navView.menu.findItem(R.id.navigation_purchase).isEnabled = false
        navView.menu.findItem(R.id.navigation_asset).isEnabled = false
        navView.menu.findItem(R.id.navigation_wallet).isEnabled = false
        navView.menu.findItem(R.id.navigation_account).isEnabled = false
    }

    private fun initListener() {
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginAccountActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.no_animation)
        }
    }



}