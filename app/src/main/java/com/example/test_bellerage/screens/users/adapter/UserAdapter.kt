package com.example.test_bellerage.screens.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.test_bellerage.R
import com.example.test_bellerage.screens.users.User

class UserAdapter(private val users: List<User>, private val onUserClick: (String) -> Unit) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(private val view: android.view.View) : RecyclerView.ViewHolder(view) {

        val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)
        val loginTextView: TextView = view.findViewById(R.id.loginTextView)
        val followersTextView: TextView = view.findViewById(R.id.followersTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.user_item,
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]

        holder.loginTextView.text = user.login
        holder.followersTextView.text = "${user.followers} followers"
        holder.avatarImageView.setImageDrawable(R.drawable.splash_logo.toDrawable())


        holder.itemView.setOnClickListener {
            onUserClick(user.login)
        }

    }

    override fun getItemCount() = users.size

}