package com.supplychainapp

import Utils.GenericDialogFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    lateinit var genericDialogFragment : GenericDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//            genericDialogFragment = GenericDialogFragment.showGenericDialog(this, false)
    }
}