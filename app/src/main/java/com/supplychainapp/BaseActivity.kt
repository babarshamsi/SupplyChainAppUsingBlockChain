package com.supplychainapp

import Utils.GetProductInfoDecider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    var getProductInfoDecider= GetProductInfoDecider()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // will be using this in future
    }


}