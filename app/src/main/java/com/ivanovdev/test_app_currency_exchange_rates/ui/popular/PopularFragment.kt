package com.ivanovdev.test_app_currency_exchange_rates.ui.popular

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.ivanovdev.domain.model.Currency
import com.ivanovdev.test_app_currency_exchange_rates.R
import com.ivanovdev.test_app_currency_exchange_rates.base.BaseFragment
import com.ivanovdev.test_app_currency_exchange_rates.databinding.FragmentPopularBinding
import com.ivanovdev.test_app_currency_exchange_rates.ui.dialog.SortDialogFragment
import com.ivanovdev.test_app_currency_exchange_rates.ui.main.MainViewModel
import com.ivanovdev.test_app_currency_exchange_rates.ui.popular.adapter.PopularAdapter
import com.ivanovdev.test_app_currency_exchange_rates.util.PreferenceHelper
import com.ivanovdev.test_app_currency_exchange_rates.util.PreferenceHelper.currency
import com.ivanovdev.test_app_currency_exchange_rates.util.PreferenceHelper.sortTypePopular
import com.ivanovdev.test_app_currency_exchange_rates.util.error.message
import com.ivanovdev.test_app_currency_exchange_rates.util.flow.collectWhileStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : BaseFragment(R.layout.fragment_popular),
    PopularAdapter.OnFavoriteClickListener {

    private lateinit var binding: FragmentPopularBinding
    private val mainViewModel by activityViewModels<MainViewModel>()
    override val viewModel by viewModels<PopularViewModel>()
    private val adapter by lazy { PopularAdapter(this) }
    private val prefs by lazy { PreferenceHelper.customPreference(
            requireContext(), PreferenceHelper.CUSTOM_PREF_NAME) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun setupInsets() {
        setupAdapter()
        swipeRefresh()
        setupListeners()
        setupUI()
        updateListFromApi()
    }

    private fun setupAdapter() {
        binding.rvPopular.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvPopular.context, DividerItemDecoration.VERTICAL
        )

        ContextCompat.getDrawable(requireContext(), R.drawable.item_divider)
            ?.let { dividerItemDecoration.setDrawable(it) }

        binding.rvPopular.addItemDecoration(dividerItemDecoration)
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getListFromApi(prefs.currency?: "EUR")
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupListeners() {
        binding.btnSort.setOnClickListener {
            mainViewModel.setFragmentIndex(0)
            SortDialogFragment().show(childFragmentManager, "sortDialog")
        }
        binding.btnChooseCurrency.setOnClickListener {
            navigate(R.id.action_popularFragment_to_currencyListFragment)
        }
    }

    private fun updateListFromApi() {
        viewModel.getListFromApi(prefs.currency?: "EUR")
    }

    private fun setupUI() {
        binding.btnChooseCurrency.text = prefs.currency
    }

    override fun observeViewModel() {
        super.observeViewModel()

        viewModel.listFromDB.collectWhileStarted(viewLifecycleOwner){ listFromDB ->
            if (listFromDB.isNullOrEmpty()){
                viewModel.setIsEmptyDB(true)
                viewModel.getListFromApi(prefs.currency?: "EUR")
            }else{
                viewModel.setPopularList(listFromDB, prefs.sortTypePopular)
            }
        }

        viewModel.popularList.collectWhileStarted(viewLifecycleOwner) { popularList ->
            if (popularList.isNullOrEmpty()){
                binding.tvNoResults.visibility = View.VISIBLE
            }else{
                binding.tvNoResults.visibility = View.INVISIBLE
                adapter.submitList(popularList)
            }
            Log.i(TAG, "Popular list = $popularList")
        }

        viewModel.error.collectWhileStarted(viewLifecycleOwner) { error ->
            snackbar(binding.root, error.message())
        }

        mainViewModel.popularSortType.collectWhileStarted(viewLifecycleOwner) {
            viewModel.sortList(it)
        }
    }

    override fun onFavoriteClick(item: Currency) {
        viewModel.updateItemFromDB(item)
    }

    companion object{
        private val TAG = PopularFragment::class.java.simpleName
    }
}