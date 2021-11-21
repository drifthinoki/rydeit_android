package com.rydeit.io.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rydeit.io.R
import com.rydeit.io.databinding.ActivityWebViewBinding
import com.rydeit.io.ui.adapter.PlanCardAdapter

class WebViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getSerializableExtra(PlanCardAdapter.KEY_WEB_URL) as String

        binding.webView.loadUrl(url)

        initListener()
    }
    
    private fun initListener() {
        binding.navTopBar.backButton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out_left)
        }
    }
}