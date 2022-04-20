package com.ivanovdev.test_app_currency_exchange_rates.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ivanovdev.test_app_currency_exchange_rates.R
import com.ivanovdev.test_app_currency_exchange_rates.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigation.visibility = View.INVISIBLE

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.popularFragment || destination.id == R.id.favoriteFragment) {
                binding.bottomNavigation.visibility = View.VISIBLE
            } else {
                binding.bottomNavigation.visibility = View.INVISIBLE
            }
        }
    }
}