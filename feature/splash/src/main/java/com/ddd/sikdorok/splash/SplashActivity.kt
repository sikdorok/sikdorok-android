package com.ddd.sikdorok.splash

import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddd.sikdorok.core_ui.base.BaseActivity
import com.ddd.sikdorok.home.HomeNavigator
import com.ddd.sikdorok.navigator.login.LoginNavigator
import com.ddd.sikdorok.splash.databinding.ActivitySplashBinding
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    // TODO : 추후 해당 화면에서 Token Validation 체크 등 유저 확인 필요

    @Inject
    lateinit var homeNavigator: HomeNavigator
    @Inject
    lateinit var signInNavigator: LoginNavigator

    override val viewModel: SplashViewModel by viewModels()

    override fun initLayout() {
        bind {
            vm = viewModel
        }

        initDynamicLinksListener()

        onBackPressedDispatcher.addCallback {
            // TODO : 추후 정책 정해야 함
            finishAffinity()
        }

        lifecycleScope.launch {
            delay(3000) // 우선 임시로 작업, 추후 변경
            viewModel.event(SplashContract.Event.LoginCheck)
        }
    }

    override fun setupCollect() {
        viewModel.effect
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach { effect ->
                when (effect) {
                    is SplashContract.Effect.GoToMain -> {
                         startActivity(homeNavigator.start(this, effect.deeplink))
                        finish()
                    }
                    is SplashContract.Effect.NaviToSignIn -> {
                        startActivity(signInNavigator.start(this))
                        finish()
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    // 다이나믹 링크 리스너 추가
    private fun initDynamicLinksListener() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                try {
                    val deepLink: String
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link.toString()
                        viewModel.event(SplashContract.Event.DeepLink(deepLink))
                    }
                } catch (_: Exception) {
                    Timber.d("Failed to receive Dynamic link by wrong situation")
                }
            }
            .addOnFailureListener(this) { e ->
                Timber.d("Failed to receive Dynamic link")
            }
    }
}
