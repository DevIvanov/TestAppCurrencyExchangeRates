package com.ivanovdev.test_app_currency_exchange_rates.ui.favorite

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
import com.ivanovdev.test_app_currency_exchange_rates.databinding.FragmentFavoriteBinding
import com.ivanovdev.test_app_currency_exchange_rates.ui.dialog.SortDialogFragment
import com.ivanovdev.test_app_currency_exchange_rates.ui.favorite.adapter.FavoriteAdapter
import com.ivanovdev.test_app_currency_exchange_rates.ui.main.MainViewModel
import com.ivanovdev.test_app_currency_exchange_rates.util.PreferenceHelper
import com.ivanovdev.test_app_currency_exchange_rates.util.PreferenceHelper.sortTypeFavorite
import com.ivanovdev.test_app_currency_exchange_rates.util.flow.collectWhileStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment(R.layout.fragment_favorite),
    FavoriteAdapter.OnDeleteClickListener{

    private lateinit var binding: FragmentFavoriteBinding
    private val mainViewModel by activityViewModels<MainViewModel>()
    override val viewModel by viewModels<FavoriteViewModel>()
    private val adapter by lazy { FavoriteAdapter(this) }
    private val prefs by lazy { PreferenceHelper.customPreference(
        requireContext(), PreferenceHelper.CUSTOM_PREF_NAME) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun setupInsets() {
        setupAdapter()
        setupListeners()
    }

    private fun setupAdapter() {
        binding.rvFavorite.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvFavorite.context, DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(requireContext(), R.drawable.item_divider)
            ?.let { dividerItemDecoration.setDrawable(it) }

        binding.rvFavorite.addItemDecoration(dividerItemDecoration)
    }

    private fun setupListeners() {
        binding.btnSort.setOnClickListener {
            mainViewModel.setFragmentIndex(1)
            SortDialogFragment().show(childFragmentManager, "sortDialog")
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()

        viewModel.currencyList.collectWhileStarted(viewLifecycleOwner) { list ->
            val favoriteList = list.filter { it.isFavorite }
            viewModel.setFavoriteList(favoriteList)
            viewModel.sortList(prefs.sortTypeFavorite)
            Log.i(TAG, "Favorite list = $favoriteList")
        }

        viewModel.favoriteList.collectWhileStarted(viewLifecycleOwner) { favoriteList ->
            adapter.submitList(favoriteList)

            if (favoriteList.isNullOrEmpty()){
                binding.tvNoResults.visibility = View.VISIBLE
            }else {
                binding.tvNoResults.visibility = View.INVISIBLE
            }
        }

        mainViewModel.favoriteSortType.collectWhileStarted(viewLifecycleOwner) {
            viewModel.sortList(it)
        }
    }

    override fun onDeleteClick(item: Currency) {
        viewModel.deleteItem(item)
    }

    companion object {
        private val TAG = FavoriteFragment::class.java.simpleName
    }
}