package com.AcvissTechnologies.acvisstesttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.AcvissTechnologies.acvisstesttask.ui.startNewActivity
import com.AcvissTechnologies.acvisstesttask.ui.welcome.SplashscreenActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val activity = SplashscreenActivity::class.java
        startNewActivity(activity)
    }
}