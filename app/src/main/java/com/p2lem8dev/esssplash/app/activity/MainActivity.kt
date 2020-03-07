package com.p2lem8dev.esssplash.app.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.common.ExceptionHandler
import com.p2lem8dev.esssplash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ExceptionHandler {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private var isSystemUIHidden = false
    fun setSystemUIVisibility(visible: Boolean) {
        isSystemUIHidden = visible
        if (visible) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            window.addFlags(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!isSystemUIHidden)
            setSystemUIVisibility(true)
    }

    override fun onException(exception: Exception): Boolean = false
}
