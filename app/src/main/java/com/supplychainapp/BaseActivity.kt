package com.supplychainapp

import Utils.GenericDialogFragment
import Utils.GetProductInfoDecider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    lateinit var genericDialogFragment : GenericDialogFragment
    var getProductInfoDecider= GetProductInfoDecider()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//            genericDialogFragment = GenericDialogFragment.showGenericDialog(this, false)
    }
}