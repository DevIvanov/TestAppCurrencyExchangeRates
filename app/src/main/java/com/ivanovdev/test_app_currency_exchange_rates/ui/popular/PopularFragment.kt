package com.ivanovdev.test_app_currency_exchange_rates.ui.popular

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.ivanovdev.test_app_currency_exchange_rates.util.error.message
import com.ivanovdev.test_app_currency_exchange_rates.util.flow.collectWhileStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : BaseFragment(R.layout.fragment_popular),
    PopularAdapter.OnFavoriteClickListener {

    private lateinit var binding: FragmentPopularBinding
    override val viewModel by viewModels<PopularViewModel>()
    private val adapter by lazy { PopularAdapter(this) }
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun setupInsets() {
        setupAdapter()
        setupListeners()
        viewModel.getPopularList()
    }

    private fun setupAdapter() {
        binding.rvPopular.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.rvPopular.context, DividerItemDecoration.VERTICAL
        )
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.item_divider))
        binding.rvPopular.addItemDecoration(dividerItemDecoration)
    }

    override fun observeViewModel() {
        super.observeViewModel()

        viewModel.popularList.collectWhileStarted(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.rvPopular.smoothScrollToPosition(0)
            Log.i(TAG, "list was submitted successfully!\nlist = $it")
        }

        viewModel.error.collectWhileStarted(viewLifecycleOwner) {
            snackbar(binding.root, it.message())
        }

        mainViewModel.itemSortDialog.collectWhileStarted(viewLifecycleOwner) { index ->
            when (index) {
                0 -> viewModel.sortByName(true)
                1 -> viewModel.sortByName(false)
                2 -> viewModel.sortByValue(true)
                3 -> viewModel.sortByValue(false)
            }
        }
    }

    private fun setupListeners() {
        binding.btnSort.setOnClickListener {
            SortDialogFragment().show(childFragmentManager, "sortDialog")
        }
    }

    override fun onFavoriteClick(item: Currency) {
        viewModel.updatePopularList(item)
        toast(R.string.ok)
    }

    companion object{
        private val TAG = PopularFragment::class.java.simpleName
    }
}