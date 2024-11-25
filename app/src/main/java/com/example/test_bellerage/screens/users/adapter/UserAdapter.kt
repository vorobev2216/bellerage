package com.example.test_bellerage.screens.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.test_bellerage.R
import com.example.test_bellerage.screens.users.DTO.UserDTORecycler
import com.squareup.picasso.Picasso

class UserAdapter(
    private var users: MutableList<UserDTORecycler>,
//    private val onUserClick: (UserDTORecycler) -> Unit,
    private val navController: NavController,

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
        holder.followersTextView.text = if (user.followers_url == null) "0 followers" else " followers"
        Picasso.get()
            .load(user.avatar_url)
            .placeholder(R.drawable.profile_image)
            .error(R.drawable.profile_image)
            .into(holder.avatarImageView)

        holder.itemView.setOnClickListener {
//            onUserClick(user)
            navController.navigate("details")
        }
    }

    override fun getItemCount() = users.size

    fun updateUsers(newUsers: List<UserDTORecycler>) {
        users.addAll(newUsers)
        notifyDataSetChanged()
    }
}