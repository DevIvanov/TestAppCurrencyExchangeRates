package com.ivanovdev.test_app_currency_exchange_rates.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ivanovdev.test_app_currency_exchange_rates.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}