package com.ivanovdev.test_app_currency_exchange_rates.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ivanovdev.test_app_currency_exchange_rates.R
import com.ivanovdev.test_app_currency_exchange_rates.ui.main.MainViewModel


class SortDialogFragment : DialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val itemsToSelect: Array<String> = resources.getStringArray(R.array.sort_array)

            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.title_sort_dialog)
                .setItems(itemsToSelect
                ) { _, which ->
                    mainViewModel.setItemSortDialog(which)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}