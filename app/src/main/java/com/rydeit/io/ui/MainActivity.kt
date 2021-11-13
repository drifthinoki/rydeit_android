package com.rydeit.io.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rydeit.io.R
import com.rydeit.io.databinding.ActivityMainBinding
import com.rydeit.io.helper.UserHelper


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
        navView.setupWithNavController(navController)

        initListener()
        registerLifeCycleObserver()
    }

    private fun initListener() {
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.no_animation)
        }
    }

    private fun registerLifeCycleObserver() {
        UserHelper.isLogin.observe(this) {
            updateNavState(it)
        }
    }

    private fun updateNavState(isLogin:Boolean) {
        val navView: BottomNavigationView = binding.navView
        navView.menu.findItem(R.id.navigation_purchase).isEnabled = isLogin
        navView.menu.findItem(R.id.navigation_asset).isEnabled = isLogin
        navView.menu.findItem(R.id.navigation_wallet).isEnabled = isLogin
        navView.menu.findItem(R.id.navigation_account).isEnabled = isLogin
    }
}