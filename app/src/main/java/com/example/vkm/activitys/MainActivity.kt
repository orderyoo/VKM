package com.example.vkm.activitys

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.vkm.SecurityData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val securityData = SecurityData(this)

        if (securityData.containsKey("token")) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}

