package com.progressingtoday.rydeit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.progressingtoday.rydeit.databinding.ActivityLoginAccountBinding


class LoginAccountActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.topBarLoginAccount.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
        }
    }
}