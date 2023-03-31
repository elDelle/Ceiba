package com.dellepiane.ceibatest.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dellepiane.ceibatest.databinding.FragmentUsersBinding
import com.dellepiane.ceibatest.domain.model.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment :
    Fragment(),
    TextWatcher {

    private lateinit var binding: FragmentUsersBinding

    private val viewModel: UsersViewModel by viewModels()

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        bindViewModel()
        binding.editTextSearchUser.addTextChangedListener(this@UsersFragment)
        viewModel.getAllUsers()
    }

    private fun configureRecyclerView() {
        binding.recyclerViewUsers.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = groupAdapter
        }
    }

    private fun bindViewModel() {
        viewModel.showLoading.observe(
            viewLifecycleOwner,
            Observer(this::showLoading)
        )
        viewModel.getUsers.observe(
            viewLifecycleOwner,
            Observer(this::handleUsers)
        )
    }

    private fun showLoading(show: Boolean) {
        binding.layoutLoading.apply {
            viewLoading.isVisible = show
            progressBarLoading.isVisible = show
        }
    }

    private fun handleUsers(users: List<User>) {
        groupAdapter.clear()
        groupAdapter.addAll(
            users.map {
                createUser(it)
            }
        )
    }

    private fun createUser(user: User): UserItemView {
        return UserItemView(user)
    }

    companion object {
        @JvmStatic
        fun newInstance() = UsersFragment()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Nothing to do
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Nothing to do
    }

    override fun afterTextChanged(editable: Editable?) {
        groupAdapter.clear()
        viewModel.getUsersByName(editable.toString())
    }
}