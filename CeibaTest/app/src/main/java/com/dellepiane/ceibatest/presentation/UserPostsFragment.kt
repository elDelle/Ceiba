package com.dellepiane.ceibatest.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dellepiane.ceibatest.databinding.FragmentUserPostsBinding
import com.dellepiane.ceibatest.domain.model.UserPost
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserPostsFragment : Fragment() {

    private lateinit var binding: FragmentUserPostsBinding

    private val viewModel: UserPostsViewModel by viewModels()

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    private val userId by lazy {
        arguments?.getInt(USER_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        bindViewModel()
        userId?.let { viewModel.getAllUserPosts(it) }
    }

    private fun configureRecyclerView() {
        binding.recyclerViewUserPosts.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = groupAdapter
        }
    }

    private fun bindViewModel() {
        viewModel.showLoading.observe(
            viewLifecycleOwner,
            Observer(this::showLoading)
        )
        viewModel.getUserPosts.observe(
            viewLifecycleOwner,
            Observer(this::handleUserPosts)
        )
        viewModel.showFailure.observe(
            viewLifecycleOwner,
            Observer(this::showError)
        )
    }

    private fun showLoading(show: Boolean) {
        binding.layoutLoading.apply {
            viewLoading.isVisible = show
            progressBarLoading.isVisible = show
        }
    }

    private fun handleUserPosts(userPosts: List<UserPost>) {
        showUserPostsList(true)
        groupAdapter.clear()
        groupAdapter.addAll(
            userPosts.map {
                createUserPost(it)
            }
        )
    }

    private fun showError(error: String) {
        showUserPostsList(false)
        binding.textViewErrorMessage.apply {
            text = error
        }
    }

    private fun showUserPostsList(show: Boolean) {
        binding.apply {
            recyclerViewUserPosts.isVisible = show
            textViewErrorMessage.isVisible = show.not()
        }
    }

    private fun createUserPost(userPost: UserPost): UserPostItemView {
        return UserPostItemView(userPost)
    }

    companion object {
        private const val USER_ID = "userId"
    }
}