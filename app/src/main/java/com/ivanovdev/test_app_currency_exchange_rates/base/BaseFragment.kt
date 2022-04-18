package com.ivanovdev.test_app_currency_exchange_rates.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ivanovdev.test_app_currency_exchange_rates.util.flow.collectWhileStarted
import com.ivanovdev.test_app_currency_exchange_rates.util.view.buildLoadingDialog
import com.ivanovdev.test_app_currency_exchange_rates.util.view.hideKeyboard
import com.ivanovdev.test_app_currency_exchange_rates.util.view.snackbar
import com.ivanovdev.test_app_currency_exchange_rates.util.view.toast

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewModel: BaseViewModel

    private var loadingDialog: Dialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupInsets()
    }

    abstract fun setupInsets()

    open fun observeViewModel() {
        viewModel.loading.collectWhileStarted(viewLifecycleOwner) { showLoading(it) }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    /* Messages */
    fun toast(@StringRes messageStringRes: Int) {
        requireContext().toast(messageStringRes)
    }

    fun snackbar(view: View, @StringRes messageStringRes: Int) {
        requireContext().snackbar(view, messageStringRes)
    }

    /* Loadings */
    fun showLoading(isLoading: Boolean) {
        loadingDialog = if (isLoading) buildLoadingDialog().apply { show() }
        else loadingDialog?.dismiss().let { null }
    }

    /* Navigation */
    fun navigate(@IdRes resId: Int) {
        findNavController().navigate(resId)
    }

    fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    fun navigate(direction: NavDirections, navOptions: NavOptions) {
        findNavController().navigate(direction, navOptions)
    }

    fun navigateBack() {
        findNavController().popBackStack()
    }

    /* Other */
    private fun hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
}