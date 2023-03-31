package com.dellepiane.ceibatest.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.dellepiane.ceibatest.R
import com.dellepiane.ceibatest.databinding.ActivityUsersBinding
import com.dellepiane.ceibatest.presentation.UsersFragment.UsersListListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersActivity :
    AppCompatActivity(),
    UsersListListener {

    private lateinit var binding: ActivityUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setupNavGraph()
    }

    private fun initViews() {
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupNavGraph() {
        findNavController(R.id.navHostFragment).setGraph(R.navigation.users_nav_graph)
    }

    private fun navigateTo(directions: NavDirections) {
        findNavController(R.id.navHostFragment).navigate(directions)
    }

    private fun currentNavController(): NavController = findNavController(R.id.navHostFragment)

    override fun onSupportNavigateUp(): Boolean {
        return when (currentNavController().currentDestination?.id) {
            R.id.fragment_users -> {
                finish()
                false
            }
            else -> {
                navigateTo(UserPostsFragmentDirections.actionToFragmentUsers())
                false
            }
        }
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    override fun onShowPostClicked(userId: Int) {
        navigateTo(UsersFragmentDirections.actionToUserPostsFragment(userId))
    }
}