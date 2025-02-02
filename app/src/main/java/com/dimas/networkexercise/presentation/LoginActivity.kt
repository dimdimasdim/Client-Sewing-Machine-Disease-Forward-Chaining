package com.dimas.networkexercise.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dimas.networkexercise.MainActivity
import com.dimas.networkexercise.base.AppModule
import com.dimas.networkexercise.databinding.ActivityLoginBinding
import com.dimas.networkexercise.domain.model.User
import com.dimas.networkexercise.presentation.viewmodel.LoginViewModel
import com.dimas.networkexercise.utils.Error
import com.dimas.networkexercise.utils.Initiate
import com.dimas.networkexercise.utils.Loading
import com.dimas.networkexercise.utils.Success
import com.dimas.networkexercise.utils.observeIn
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private var loginViewModel: LoginViewModel? = null

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiate()
        observer()

        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            loginViewModel?.postLogin(username, password)
        }
    }

    private fun initiate() {
        loginViewModel =
            ViewModelProvider(this, AppModule.loginViewModelFactory)[LoginViewModel::class.java]
    }

    private fun observer() {
        loginViewModel?.login?.observeIn(this) {
            when (it) {
                is Success -> storeToken(it.data)
                is Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                is Loading -> showLoader(it.isLoading)
                is Initiate -> {}
                else -> {}
            }
        }
    }

    private fun storeToken(data: User) {
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