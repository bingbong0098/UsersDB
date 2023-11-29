package com.example.usersdb.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.usersdb.databinding.UserItemLayoutBinding
import com.example.usersdb.db.UserEntity
import com.example.usersdb.repository.UserRepository
import com.example.usersdb.ui.main.MainActivity
import com.example.usersdb.ui.update.UpdateActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UsersAdapter @Inject constructor(@ApplicationContext val context: Context) :
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {


    @Inject
    lateinit var repository: UserRepository

    lateinit var binding: UserItemLayoutBinding

    lateinit var listener : userClickListiner

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.MyViewHolder {
        binding = UserItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder()
    }

    override fun onBindViewHolder(holder: UsersAdapter.MyViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
//        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    val list = mutableListOf<Int?>()
    inner class MyViewHolder() : ViewHolder(binding.root) {
        @SuppressLint("SuspiciousIndentation")
        fun bind(item: UserEntity) {

            binding.apply {
                nameTxt.text = item.name
                familyTxt.text = item.family
                ageTxt.text = item.age.toString()
                nationalNumberTxt.text = item.nationalNumber.toString()
                checkBox2.setOnCheckedChangeListener { CheckBox, isCheked ->
                    if (isCheked == true){
                        list.add(item.id)
                    }
                        listener?.users(list)
                }
                root.setOnClickListener {
                    val intent = Intent(context, UpdateActivity::class.java)
                    intent.putExtra("bundle_user_id", item.id)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
        }
    }
    interface userClickListiner {
        fun users (users: MutableList<Int?>)
    }


    private val differCallback = object : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}