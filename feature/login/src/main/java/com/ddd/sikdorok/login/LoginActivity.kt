package com.ddd.sikdorok.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ddd.sikdorok.login.databinding.ActivityLoginBinding
import com.ddd.sikdorok.navigator.signin.SignInNavigator
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
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

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        binding.viewKakao.setOnClickListener {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }

        val hashkey = Utility.getKeyHash(this)
        Log.e("hash", hashkey)
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "KAKAO"
    }
}
