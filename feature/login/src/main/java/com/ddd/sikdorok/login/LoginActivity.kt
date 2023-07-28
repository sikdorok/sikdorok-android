package com.ddd.sikdorok.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ddd.sikdorok.login.databinding.ActivityLoginBinding
import com.ddd.sikdorok.navigator.signin.SignInNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var signInNavigator: SignInNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.viewEmail.setOnClickListener {
            startActivity(signInNavigator.start(this))
        }
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }
}
