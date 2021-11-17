package com.rydeit.io.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rydeit.io.MainViewModel
import com.rydeit.io.R
import com.rydeit.io.databinding.ActivityMainBinding
import com.rydeit.io.helper.UserHelper


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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
        viewModel.isLogin.observe(this) {
            updateUI(it)
        }
    }

    private fun updateUI(isLogin:Boolean) {
        // update main top bar state
        binding.loginButton.visibility = if (isLogin) View.GONE else View.VISIBLE
        binding.clUser.visibility =  if (isLogin) View.VISIBLE else View.GONE
        viewModel.getUser()?.let { user ->
            binding.imageProfile.text = user.firstAlphabetOfEmail.toString()
            binding.tvEmail.text = user.email
            binding.tvLevel.text = user.formattedLevel
        }


        // update bottom nav view state
        val navView: BottomNavigationView = binding.navView
        navView.menu.findItem(R.id.navigation_purchase).isEnabled = isLogin
        navView.menu.findItem(R.id.navigation_asset).isEnabled = isLogin
        navView.menu.findItem(R.id.navigation_wallet).isEnabled = isLogin
        navView.menu.findItem(R.id.navigation_account).isEnabled = isLogin
    }
}