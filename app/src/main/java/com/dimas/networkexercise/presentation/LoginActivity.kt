package com.dimas.networkexercise.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.dimas.networkexercise.MainActivity
import com.dimas.networkexercise.base.AppModule
import com.dimas.networkexercise.databinding.ActivityLoginBinding
import com.dimas.networkexercise.domain.model.User
import com.dimas.networkexercise.presentation.viewmodel.LoginViewModel
import com.dimas.networkexercise.utils.Error
import com.dimas.networkexercise.utils.Initiate
import com.dimas.networkexercise.utils.Loading
import com.dimas.networkexercise.utils.Success
import com.dimas.networkexercise.utils.UserSession
import com.dimas.networkexercise.utils.observeIn


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val npm = binding.edtNPM.text.toString().trim()
            val fullName = binding.edtFullName.text.toString().trim()
            storeUser(npm, fullName)
        }
    }

    private fun storeUser(npm: String, fullName: String) {
        UserSession.setUser(fullName, npm)
        moveToMain()
    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoader(isLoading: Boolean) {
        with(binding) {
            btnLogin.isVisible = !isLoading
            pbLogin.isVisible = isLoading
        }
    }


}