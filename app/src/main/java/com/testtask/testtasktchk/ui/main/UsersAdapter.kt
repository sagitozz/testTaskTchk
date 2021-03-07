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
    var currentPage = 1 // Текущая страница
    lateinit var callback: Callback

    var users = listOf<User>()

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

        if (position == users.size - 1) {
            // Достигнут конец списка, нужно подгрузить новые данные
            callback.onLoadUsersForNextPage(++currentPage)
        }
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
                    .into(findViewById<ImageView>(R.id.user_avatar_image))
            }
        }
    }

    interface Callback {
        // Уведомляет о достижении конца списка и передает номер следующей страницы
        // для которой нужно загрузить новую порцию данных
        fun onLoadUsersForNextPage(nextPage: Int)
    }
}
