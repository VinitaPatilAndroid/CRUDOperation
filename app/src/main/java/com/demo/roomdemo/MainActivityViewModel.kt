package com.demo.roomdemo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.roomdemo.db.RoomAppDb
import com.demo.roomdemo.db.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(app: Application): AndroidViewModel(app) {

     var allUsers : MutableLiveData<List<UserEntity>> =  MutableLiveData()

    init{
        getAllUsers()
    }

    fun getAllUsersObservers(): MutableLiveData<List<UserEntity>> {
        return allUsers
    }

    private fun getAllUsers() = viewModelScope.launch(Dispatchers.IO){
        val userDao = RoomAppDb.getDatabase((getApplication()))?.userDao()
        val list = userDao?.getAllUserInfo()

        allUsers.postValue(list)
    }

     fun insertUserInfo(entity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        val userDao = RoomAppDb.getDatabase(getApplication())?.userDao()
        userDao?.insertUser(entity)
        getAllUsers()
    }

    fun updateUserInfo(entity: UserEntity) = viewModelScope.launch(Dispatchers.IO){
        val userDao = RoomAppDb.getDatabase(getApplication())?.userDao()
        userDao?.updateUser(entity)
        getAllUsers()
    }

     fun deleteUserInfo(entity: UserEntity) = viewModelScope.launch(Dispatchers.IO){
        val userDao = RoomAppDb.getDatabase(getApplication())?.userDao()
        userDao?.deleteUser(entity)
        getAllUsers()
    }
}