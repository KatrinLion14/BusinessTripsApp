package com.example.businesstripsapp.splash_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.accounts.AccountManager
import android.annotation.SuppressLint
import com.example.businesstripsapp.R
import com.example.businesstripsapp.main_activity.MainActivity
import com.example.businesstripsapp.start_activity.StartActivity
import retrofit2.Call
import retrofit2.Response

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }

}
