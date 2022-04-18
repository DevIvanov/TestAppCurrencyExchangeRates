package com.ivanovdev.test_app_currency_exchange_rates.util.view

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat.createBlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.ivanovdev.test_app_currency_exchange_rates.R

fun Context.toast(@StringRes messageStringRes: Int) {

    val toast = Toast.makeText(this, messageStringRes, Toast.LENGTH_SHORT)
    toast.show()
}
