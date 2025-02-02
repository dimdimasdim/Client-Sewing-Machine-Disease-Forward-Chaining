package com.dimas.networkexercise.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dimas.networkexercise.MainActivity
import com.dimas.networkexercise.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch {
            delay(2000)
            decideNextPage()
            finish()
        }
    }

    private fun decideNextPage() {
        val i = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(i)
    }
}