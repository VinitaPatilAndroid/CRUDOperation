package com.demo.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.demo.roomdemo.db.UserEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.RowClickListener {

    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(divider)
        }

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getAllUsersObservers().observe(this, Observer {
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        })

        saveButton.setOnClickListener {
            val name  = nameInput.text.toString()
            val email  = emailInput.text.toString()


            if(saveButton.text.equals("Save")) {
                val user = UserEntity(0, name, email)
                viewModel.insertUserInfo(user)
            } else {
                val user = UserEntity(nameInput.getTag(nameInput.id).toString().toInt(), name, email)

                viewModel.updateUserInfo(user)
                saveButton.setText("Save")
            }
            nameInput.setText("")
            emailInput.setText("")
        }
    }



    override fun onDeleteUserClickListener(user: UserEntity) {
      //  viewModel.deleteUserInfo(user)
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                viewModel.deleteUserInfo(user)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    override fun onItemClickListener(user: UserEntity) {
        nameInput.setText(user.name)
        emailInput.setText(user.email)
        nameInput.setTag(nameInput.id, user.id)
        saveButton.setText("Update")
    }
}