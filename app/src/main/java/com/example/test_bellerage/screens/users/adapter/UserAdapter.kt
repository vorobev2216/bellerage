package com.example.test_bellerage.screens.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test_bellerage.R
import com.example.test_bellerage.databinding.UserItemBinding
import com.example.test_bellerage.screens.users.DTO.User

// TODO тут тоже binding можно было поиспользовать

class UserAdapter(
    private var users: MutableList<User>, // TODO передаешь реализацию листа
    private val onUserClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)
        val loginTextView: TextView = view.findViewById(R.id.loginTextView)
        val followersTextView: TextView = view.findViewById(R.id.followersTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.loginTextView.text = user.login
        holder.followersTextView.text = if (user.followersUrl == null) "0 followers" else  "followers"
        holder.avatarImageView.setImageResource(R.drawable.profile_image) // TODO а как же аватарки???

        holder.itemView.setOnClickListener {
            onUserClick(user)
        }
    }

    override fun getItemCount() = users.size

    fun updateUsers(newUsers: List<User>) {
        users.addAll(newUsers)
        notifyDataSetChanged()
    }
}