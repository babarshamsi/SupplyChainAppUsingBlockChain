package com.supplychainapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.supplychainapp.R


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        /****** Create Thread that will sleep for 5 seconds****/
        /****** Create Thread that will sleep for 5 seconds */
        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep((5 * 1000).toLong())

                    // After 5 seconds redirect to another intent
                    val i = Intent(baseContext, MainActivity::class.java)
                    startActivity(i)

                    //Remove activity
                    finish()
                } catch (e: Exception) {
                }
            }
        }
        // start thread
        // start thread
        background.start()
    }
}