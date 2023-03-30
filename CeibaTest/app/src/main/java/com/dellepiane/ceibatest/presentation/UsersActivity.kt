package com.dellepiane.ceibatest.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dellepiane.ceibatest.R
import com.dellepiane.ceibatest.databinding.ActivityUsersBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class UsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initFragments()
    }

    private fun initViews() {
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initFragments() {
        addFragment(UsersFragment.newInstance())
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, fragment)
            .show(fragment)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}