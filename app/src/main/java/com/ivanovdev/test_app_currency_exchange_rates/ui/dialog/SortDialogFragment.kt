package com.ivanovdev.test_app_currency_exchange_rates.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ivanovdev.test_app_currency_exchange_rates.R
import com.ivanovdev.test_app_currency_exchange_rates.ui.main.MainViewModel
import com.ivanovdev.test_app_currency_exchange_rates.util.PreferenceHelper
import com.ivanovdev.test_app_currency_exchange_rates.util.PreferenceHelper.sortTypeFavorite
import com.ivanovdev.test_app_currency_exchange_rates.util.PreferenceHelper.sortTypePopular


class SortDialogFragment : DialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val prefs by lazy { PreferenceHelper.customPreference(
        requireContext(), PreferenceHelper.CUSTOM_PREF_NAME) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val itemsToSelect: Array<String> = resources.getStringArray(R.array.sort_array)

            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.title_sort_dialog)
                .setItems(itemsToSelect
                ) { _, which ->
                    if (mainViewModel.fragmentIndex.value == 0){
                        prefs.sortTypePopular = which
                        mainViewModel.setPopularSortType(which)
                    } else{
                        prefs.sortTypeFavorite = which
                        mainViewModel.setFavoriteSortType(which)
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}