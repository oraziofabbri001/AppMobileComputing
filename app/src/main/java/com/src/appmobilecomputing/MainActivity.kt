package com.src.appmobilecomputing

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.src.appmobilecomputing.databinding.ActivityMainBinding

//https://github.com/oraziofabbri001/AppMobileComputing

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        val policyStrictMode = ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policyStrictMode);

        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        val navView: BottomNavigationView = binding.navView;
        val navController = findNavController(R.id.nav_host_fragment_activity_main);
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_bluetooth, R.id.navigation_orarilezioni
            )
        );
        setupActionBarWithNavController(navController, appBarConfiguration);
        navView.setupWithNavController(navController);

    }
}