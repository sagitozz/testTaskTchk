package com.testtask.testtasktchk.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.testtask.testtasktchk.R
import com.testtask.testtasktchk.data.entities.User

/**
 * @autor d.snytko
 */
class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer(this, UserDiffUtilCallback())

    var users: List<User> = listOf()
        set(value) {
            differ.submitList(value)
            field = value
        }
        get() = differ.currentList

    fun update(dataList: List<User>) {
        users = dataList.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val user = users[position]

            with(itemView) {
                findViewById<TextView>(R.id.user_login).text = user.login
                findViewById<TextView>(R.id.user_id).text = user.id.toString()

                Picasso.get()
                    .load(user.avatarUrl)
                    .error(R.drawable.ic_logout_24px)
                    .placeholder(R.drawable.ic_logout_24px)
                    .into(findViewById<ImageView>(R.id.user_avatar))
            }
        }
    }
}
