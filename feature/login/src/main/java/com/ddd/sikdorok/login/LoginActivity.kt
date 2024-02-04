package com.ddd.sikdorok.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.core_design.R
import com.ddd.sikdorok.core_ui.base.BaseActivity
import com.ddd.sikdorok.extensions.showSnackBar
import com.ddd.sikdorok.home.HomeNavigator
import com.ddd.sikdorok.login.databinding.ActivityLoginBinding
import com.ddd.sikdorok.navigator.signin.SignInNavigator
import com.ddd.sikdorok.signup.SignUpNavigator
import com.google.android.material.snackbar.Snackbar
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    @Inject
    lateinit var signInNavigator: SignInNavigator

    @Inject
    lateinit var signUpNavigator: SignUpNavigator

    @Inject
    lateinit var homeNavigator: HomeNavigator

    override val viewModel by viewModels<LoginViewModel>()

    override fun initLayout() {
        if (viewModel.isFromDelete) {
            showSnackBar(
                view = binding.root,
                message = "계정 삭제가 완료되었습니다",
                backgroundColor = R.color.text_color,
                textColor = R.color.white,
                duration = Snackbar.LENGTH_LONG
            )
        }
    }

    private val callback: (token: OAuthToken?, error: Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오 로그인 실패")
        } else if (token != null) {
            viewModel.event(LoginContract.Event.CheckKakaoUser(token.accessToken))
        }
    }

    private val signUpRegisterCallback =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                startActivity(homeNavigator.start(this))
                finish()
            }
        }

    override fun setupCollect() {
        viewModel.effect
            .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach { effect ->
                when (effect) {
                    is LoginContract.SideEffect.NaviToSikdorokLogin -> {
                        startActivity(signInNavigator.start(this))
                    }
                    is LoginContract.SideEffect.NaviToSignUp -> {
                        signUpRegisterCallback.launch(
                            signUpNavigator.start(
                                this,
                                effect.email.orEmpty(),
                                effect.oauthId,
                                effect.oauthType
                            )
                        )
                    }
                    is LoginContract.SideEffect.NaviToKakaoLogin -> {
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    }
                    is LoginContract.SideEffect.NaviToHome -> {
                        startActivity(homeNavigator.start(this))
                        finish()
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.viewEmail.setOnClickListener {
            viewModel.event(LoginContract.Event.RequestSikdorokLogin)
        }

        binding.viewKakao.setOnClickListener {
            viewModel.event(LoginContract.Event.RequestKakaoLogin)
        }
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "KAKAO"
    }
}
