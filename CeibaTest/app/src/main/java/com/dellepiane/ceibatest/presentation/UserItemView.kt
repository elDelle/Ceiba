package com.dellepiane.ceibatest.presentation

import android.view.View
import com.dellepiane.ceibatest.R
import com.dellepiane.ceibatest.databinding.ViewUserBinding
import com.dellepiane.ceibatest.domain.model.User
import com.xwray.groupie.viewbinding.BindableItem

class UserItemView(
    private val listener: UserItemListener?,
    private val user: User
) : BindableItem<ViewUserBinding>() {

    private lateinit var binding: ViewUserBinding

    override fun getLayout() = R.layout.view_user

    override fun initializeViewBinding(view: View): ViewUserBinding =
        ViewUserBinding.bind(view)

    override fun bind(viewBinding: ViewUserBinding, position: Int) {
        binding = viewBinding
        setupView()
    }

    private fun setupView() {
        binding.apply {
            textViewName.text = user.name
            textViewPhone.text = user.phone
            textViewMail.text = user.email
            textViewShowPosts.setOnClickListener {
                user.id?.let {
                    listener?.onShowPostsClicked(it)
                }
            }
        }
    }

    interface UserItemListener {
        fun onShowPostsClicked(userId: Int)
    }
}
