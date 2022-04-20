package com.ivanovdev.test_app_currency_exchange_rates.ui.currency_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.ivanovdev.domain.model.Currency
import com.ivanovdev.test_app_currency_exchange_rates.R
import com.ivanovdev.test_app_currency_exchange_rates.base.BaseFragment
import com.ivanovdev.test_app_currency_exchange_rates.databinding.FragmentCurrencyListBinding
import com.ivanovdev.test_app_currency_exchange_rates.ui.currency_list.adapter.CurrencyListAdapter
import com.ivanovdev.test_app_currency_exchange_rates.util.PreferenceHelper
import com.ivanovdev.test_app_currency_exchange_rates.util.PreferenceHelper.currency
import com.ivanovdev.test_app_currency_exchange_rates.util.flow.collectWhileStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyListFragment : BaseFragment(R.layout.fragment_currency_list),
    CurrencyListAdapter.OnItemClickListener{

    override val viewModel by viewModels<CurrencyListViewModel>()
    private lateinit var binding: FragmentCurrencyListBinding
    private val adapter by lazy { CurrencyListAdapter(this) }
    private val prefs by lazy { PreferenceHelper.customPreference(
        requireContext(), PreferenceHelper.CUSTOM_PREF_NAME) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun setupInsets() {
        setupAdapter()
        setupListeners()
        setupTextWatcher()
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            filterList(s)
        }
    }

    private fun filterList(s: CharSequence?){
        val newList = viewModel.currentList.value.filter{
            it.id.contains(s!!, true)
        }
        viewModel.setEditedList(newList)
    }

    private fun setupTextWatcher() {
        binding.edtCurrencyName.addTextChangedListener(textWatcher)
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

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            navigateBack()
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()

        viewModel.currencyList.collectWhileStarted(viewLifecycleOwner) { currencyList ->
            if (viewModel.currentList.value.isEmpty())
                viewModel.setCurrentList(currencyList)
        }

        viewModel.currentList.collectWhileStarted(viewLifecycleOwner) { currentList ->
            if (binding.edtCurrencyName.text.isNullOrEmpty())
                viewModel.setEditedList(currentList)
        }

        viewModel.editedList.collectWhileStarted(viewLifecycleOwner) { editedList ->
            adapter.submitList(editedList)
        }
    }

    override fun onItemClick(item: Currency) {
        prefs.currency = item.id
        navigate(R.id.action_currencyListFragment_to_popularFragment)
    }
}