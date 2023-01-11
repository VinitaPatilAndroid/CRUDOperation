package com.demo.roomdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.roomdemo.db.UserEntity
import kotlinx.android.synthetic.main.recyclerview_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewAdapter(/*val listener: RowClickListener*/
    private val onItemClick:(UserEntity) -> Unit,private val deleteUser:(UserEntity)-> Unit
): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var items  = ArrayList<UserEntity>()

    fun setListData(data: ArrayList<UserEntity>) {
        this.items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
       val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {

        /*holder.itemView.setOnClickListener {
            listener.onItemClickListener(items[position])
        }*/
        holder.itemView.setOnClickListener { 
           onItemClick(items[position])
        }
        holder.bind(items[position])
        holder.deleteUserID.setOnClickListener {
            deleteUser(items[position])
        }

    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvName = view.tvName
        val tvEmail = view.tvEmail
        val tvPhone = view.tvPhone
        val deleteUserID = view.deleteUserID



        fun bind(data: UserEntity) {
            tvName.text = data.name
            tvEmail.text = data.email

          //  val currentTime: Date = Calendar.getInstance().getTime()
            tvPhone.text = getDate.getCurrentDateTime().toString()

           /* deleteUserID.setOnClickListener {
                listener.onDeleteUserClickListener(data)
            }*/

        }
    }

 /*   interface RowClickListener{
        fun onDeleteUserClickListener(user: UserEntity)
        fun onItemClickListener(user: UserEntity)
    }*/
}