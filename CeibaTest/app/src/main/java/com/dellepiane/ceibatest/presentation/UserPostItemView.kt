package com.dellepiane.ceibatest.presentation

import android.view.View
import com.dellepiane.ceibatest.R
import com.dellepiane.ceibatest.databinding.ViewUserPostBinding
import com.dellepiane.ceibatest.domain.model.UserPost
import com.xwray.groupie.viewbinding.BindableItem

class UserPostItemView(private val userPost: UserPost) : BindableItem<ViewUserPostBinding>() {

    private lateinit var binding: ViewUserPostBinding

    override fun getLayout() = R.layout.view_user_post

    override fun initializeViewBinding(view: View): ViewUserPostBinding =
        ViewUserPostBinding.bind(view)

    override fun bind(viewBinding: ViewUserPostBinding, position: Int) {
        binding = viewBinding
        setupView()
    }

    private fun setupView() {
        binding.apply {
            textViewTitle.text = userPost.title
            textViewPost.text = userPost.body
        }
    }
}
