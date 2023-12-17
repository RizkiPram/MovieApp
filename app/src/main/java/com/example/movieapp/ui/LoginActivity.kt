package com.example.movieapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.size
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.data.local.datastore.UserPreferences
import com.example.movieapp.databinding.ActivityLoginBinding
import com.example.movieapp.viewmodel.AuthViewModel
import com.example.movieapp.viewmodel.AuthViewModelFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(UserPreferences.getInstance(dataStore))
        )[AuthViewModel::class.java]
        viewModel.getUser().observe(this@LoginActivity){
            if (it){
                moveToMainActivity()
            }
        }
        binding.apply {
            binding.btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (!isValidEmail(email)) {
                    layoutEmail.error = "Email Tidak Valid"
                }else{
                    layoutEmail.error=null
                }
                if (password.length < 8) {
                    layoutPassword.error = "Password harus lebih dari 8"
                }else{
                    layoutPassword.error=null
                }
                if (isValidEmail(email) && password.length > 8) {
                    progressBar.visibility= View.VISIBLE
                    layoutPassword.error=null
                    layoutEmail.error=null
                    Handler().postDelayed({
                        viewModel.saveUser(true)
                        progressBar.visibility= View.GONE
                        moveToMainActivity()
                    },3000)
                }
            }

        }
    }
    private fun moveToMainActivity(){
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}