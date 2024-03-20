package com.example.notes.by.reex

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.notes.by.reex.databinding.ActivityMainBinding
import com.example.notes.by.reex.utils.APP_ACTIVITY

class MainActivity : AppCompatActivity() {
    private lateinit var mToolbar: Toolbar
    lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        APP_ACTIVITY = this
        mToolbar = mBinding.toolbar
        setSupportActionBar(mToolbar)
        title = "Notes by Reex"

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
