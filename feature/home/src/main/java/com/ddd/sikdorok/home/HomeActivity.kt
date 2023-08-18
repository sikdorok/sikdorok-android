package com.ddd.sikdorok.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        // 딥링크 연동 테스트. 추후 삭제
        val deeplink = intent.getStringExtra(KEY_DEEPLINK)
        Toast.makeText(this, deeplink.orEmpty(), Toast.LENGTH_LONG).show()
    }

    companion object {
        const val KEY_DEEPLINK = "deeplink"
    }
}