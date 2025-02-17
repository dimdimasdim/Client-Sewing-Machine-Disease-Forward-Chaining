package com.dimas.networkexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.dimas.networkexercise.presentation.adapter.MainPagerAdapter
import com.dimas.networkexercise.databinding.ActivityMainBinding
import com.dimas.networkexercise.presentation.HomeFragment
import com.dimas.networkexercise.presentation.ProfileFragment
import com.dimas.networkexercise.utils.UserSession

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeToolbar()
        initializeViewPager()
    }

    private fun initializeViewPager() {
        val adapter = MainPagerAdapter(this)
        adapter.addFragment(HomeFragment.newInstance())
        adapter.addFragment(ProfileFragment.newInstance())
        with(binding) {
            vpMain.adapter = adapter
            TabLayoutMediator(tabMain, vpMain) { tab, position ->
                when(position) {
                    0 -> tab.text = getString(R.string.label_home)
                    1 -> tab.text = getString(R.string.label_profile)
                }
            }.attach()
        }
    }

    private fun initializeToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Hi, ${UserSession.getName()}"
            setDisplayHomeAsUpEnabled(false)
        }
    }
}