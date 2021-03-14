package com.testtask.testtasktchk.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.testtask.testtasktchk.data.entities.User

/**
 * @autor d.snytko
 */
class UserDiffUtilCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}
