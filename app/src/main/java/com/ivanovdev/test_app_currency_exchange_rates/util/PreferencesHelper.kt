package com.ivanovdev.test_app_currency_exchange_rates.util


import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    const val CUSTOM_PREF_NAME = "CURRENCY_DATA"
    private const val MAIN_CURRENCY = "MAIN_CURRENCY"
    private const val SORT_TYPE_POPULAR = "SORT_TYPE_POPULAR"
    private const val SORT_TYPE_FAVORITE = "SORT_TYPE_FAVORITE"


    fun customPreference(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.currency
        get() = getString(MAIN_CURRENCY, "EUR")
        set(value) {
            editMe {
                it.putString(MAIN_CURRENCY, value)
            }
        }

    var SharedPreferences.sortTypePopular
        get() = getInt(SORT_TYPE_POPULAR, 0)
        set(value) {
            editMe {
                it.putInt(SORT_TYPE_POPULAR, value)
            }
        }

    var SharedPreferences.sortTypeFavorite
        get() = getInt(SORT_TYPE_FAVORITE, 0)
        set(value) {
            editMe {
                it.putInt(SORT_TYPE_FAVORITE, value)
            }
        }
}
