package com.demo.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.demo.roomdemo.db.UserEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ToDoActivity : AppCompatActivity()/*, RecyclerViewAdapter.RowClickListener*/ {

   lateinit var recyclerViewAdapter: RecyclerViewAdapter

   private val viewModel :MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var demoAdapter = RecyclerViewAdapter(::onItemClickListener,::onDeleteUserClickListener)

   /*     recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(divider)
        }*/

        with(recyclerView){
            layoutManager = LinearLayoutManager(this@ToDoActivity)
            recyclerViewAdapter = demoAdapter
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(divider)
        }

        viewModel.getAllUsersObservers().observe(this, Observer {
            recyclerViewAdapter.setListData(ArrayList(it))
        })

        saveButton.setOnClickListener {
            val name  = nameInput.text.toString()
            val email  = emailInput.text.toString()

            if(saveButton.text.equals(Constants.SAVE)) {
                val user = UserEntity(0, nameInput.text.toString(), emailInput.text.toString())
                viewModel.insertUserInfo(user)

            } else {
                val user = UserEntity(nameInput.getTag(nameInput.id).toString().toInt(), name, email)
                viewModel.updateUserInfo(user)
                saveButton.setText(Constants.SAVE)
            }
            nameInput.setText("")
            emailInput.setText("")
        }
    }



     fun onDeleteUserClickListener(user: UserEntity) {
      //  viewModel.deleteUserInfo(user)
        val builder = AlertDialog.Builder(this@ToDoActivity)
        builder.setMessage(Constants.ARE_YOU_SURE_WANT_TO_DELETE)
            .setCancelable(false)
            .setPositiveButton(Constants.YES) { dialog, id ->
                GlobalScope.launch {
                    viewModel.deleteUserInfo(user)
                }

            }
            .setNegativeButton(Constants.NO) { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    fun onItemClickListener(user: UserEntity) {
        nameInput.setText(user.name)
        emailInput.setText(user.email)
        nameInput.setTag(nameInput.id, user.id)
        saveButton.setText(Constants.UPDATE)
    }
}