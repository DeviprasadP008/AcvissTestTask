package com.AcvissTechnologies.acvisstesttask.ui.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.AcvissTechnologies.acvisstesttask.ui.home.HomeActivity
import com.AcvissTechnologies.acvisstesttask.ui.startNewActivity
import java.util.*
import com.AcvissTechnologies.acvisstesttask.R


class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val activity = HomeActivity::class.java
                startNewActivity(activity)
            }
        }, 3000)

    }
}